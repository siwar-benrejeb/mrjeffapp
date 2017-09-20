package com.mrjeffapp.product.service.impl;


import com.mrjeffapp.product.api.dto.ProductRequest;
import com.mrjeffapp.product.client.CouponServiceClient;
import com.mrjeffapp.product.client.coupon.model.CouponCalculateRequest;
import com.mrjeffapp.product.client.coupon.model.CouponCalculateResponse;
import com.mrjeffapp.product.client.coupon.model.CouponRedeemRequest;
import com.mrjeffapp.product.client.coupon.model.CouponRedeemResponse;
import com.mrjeffapp.product.exception.CustomerNotFoundCoupon;
import com.mrjeffapp.product.exception.InvalidCouponException;
import com.mrjeffapp.product.exception.ProductNotFoundException;
import com.mrjeffapp.product.domain.PriceCalculation;
import com.mrjeffapp.product.domain.Product;
import com.mrjeffapp.product.domain.VATRate;
import com.mrjeffapp.product.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductPriceCalculatorServiceImplTest {

    private static final String COUPON = "COUPON_CODE";
    private static final Double COUPON_AMOUNT = 5D;
    private static final String ORDER_ID = "1";
    private static final String CUSTOMER_ID = "2";

    private static final String PRODUCT1_ID = "3";
    private static final String PRODUCT1_CODE = "CODE1";
    private static final Double PRODUCT1_PRICE = 100D;
    private static final Integer PRODUCT1_QUANTITY = 10;
    private static final Double PRODUCT1_VAT = 10D;

    private static final String PRODUCT2_ID = "4";
    private static final String PRODUCT2_CODE = "CODE2";
    private static final Double PRODUCT2_PRICE = 100D;
    private static final Integer PRODUCT2_QUANTITY = 10;
    private static final Double PRODUCT2_VAT = 10D;

    private static final Double ORDER_DISCOUNT = 500D;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CouponServiceClient couponServiceClient;

    @InjectMocks
    private ProductPriceCalculatorServiceImpl productsPriceCalculatorServiceImpl;

    private Collection<String> productCodes;

    @Before
    public void setUp() {
        productCodes = Stream.of(PRODUCT1_CODE, PRODUCT2_CODE)
                                    .collect(Collectors.toSet());

        CouponRedeemResponse couponRedeemResponse = new CouponRedeemResponse();
        couponRedeemResponse.setOrderAmountDiscount(ORDER_DISCOUNT);
        couponRedeemResponse.setCouponAmount(COUPON_AMOUNT);

        CouponCalculateResponse couponCalculateResponse = new CouponCalculateResponse();
        couponCalculateResponse.setOrderAmountDiscount(ORDER_DISCOUNT);
        couponCalculateResponse.setCouponAmount(COUPON_AMOUNT);

        when(couponServiceClient.calculateCouponDiscount(any(CouponCalculateRequest.class))).thenReturn(couponCalculateResponse);
        when(couponServiceClient.calculateAndRedeemCouponDiscount(any(CouponRedeemRequest.class))).thenReturn(couponRedeemResponse);
        when(productRepository.findByCodeInAndActiveTrue(productCodes)).thenReturn(getProducts());
    }

    public List<ProductRequest> getProductsRequests() {
        ProductRequest productRequest1 = new ProductRequest();
        productRequest1.setCode(PRODUCT1_CODE);
        productRequest1.setQuantity(PRODUCT1_QUANTITY);

        ProductRequest productRequest2 = new ProductRequest();
        productRequest2.setCode(PRODUCT2_CODE);
        productRequest2.setQuantity(PRODUCT2_QUANTITY);

        return Arrays.asList(productRequest1, productRequest2);
    }

    private List<Product> getProducts() {
        VATRate vatRate = new VATRate();
        vatRate.setPercentage(PRODUCT1_VAT);

        Product product1 = new Product();
        product1.setId(PRODUCT1_ID);
        product1.setCode(PRODUCT1_CODE);
        product1.setPrice(PRODUCT1_PRICE);
        product1.setVatRate(vatRate);

        Product product2 = new Product();
        product2.setId(PRODUCT2_ID);
        product2.setCode(PRODUCT2_CODE);
        product2.setPrice(PRODUCT2_PRICE);
        product2.setVatRate(vatRate);

        return Arrays.asList(product1, product2);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testErrorProductNotFoundExceptionCalculateCartPrice() {
        Collection<String> productCodes = Stream.of(PRODUCT1_CODE, PRODUCT2_CODE)
                .collect(Collectors.toSet());

        when(productRepository.findByCodeInAndActiveTrue(productCodes)).thenReturn(new HashSet<Product>());

        productsPriceCalculatorServiceImpl.calculatePrice(getProductsRequests(), CUSTOMER_ID);
    }

    @Test(expected = CustomerNotFoundCoupon.class)
    public void testErrorOrderCustomerNotFoundCoupon() {
        productsPriceCalculatorServiceImpl.calculateOrderPriceWithCoupon(getProductsRequests(), null, COUPON);
    }

    @Test
    public void testCalculatePrice() {
        PriceCalculation result =  productsPriceCalculatorServiceImpl.calculatePrice(getProductsRequests(), CUSTOMER_ID);

        assertThat("The total price calculated should be 2000", result.getTotalPrice(), is(2000D));
        assertThat("The total price without discount should be 2000", result.getTotalPriceWithoutDiscount(), is(2000D));
        assertThat("The discount should be 0", result.getDiscount(), is(0D));
    }

    @Test
    public void testCalculateCartPriceWithCoupon() {
        PriceCalculation result =  productsPriceCalculatorServiceImpl.calculateCartPriceWithCoupon(getProductsRequests(), CUSTOMER_ID, COUPON);

        verify(couponServiceClient).calculateCouponDiscount(any(CouponCalculateRequest.class));
        verify(couponServiceClient, never()).calculateAndRedeemCouponDiscount(any(CouponRedeemRequest.class));

        assertThat("The total price calculated with discount should be 1500", result.getTotalPrice(), is(1500D));
        assertThat("The total price without discount should be 2000", result.getTotalPriceWithoutDiscount(), is(2000D));
        assertThat("The discount should be 0", result.getDiscount(), is(500D));
        assertThat("The coupon amount should be 5", result.getCouponAmount(), is(5D));
        assertThat("Coupon should be valid", result.getCouponValid(), is(true));
    }

    @Test
    public void testCalculateCartPriceWithCouponInvalid() {
        when(couponServiceClient.calculateCouponDiscount(any(CouponCalculateRequest.class))).thenThrow(new InvalidCouponException("Invalid coupon"));

        PriceCalculation result =  productsPriceCalculatorServiceImpl.calculateCartPriceWithCoupon(getProductsRequests(), CUSTOMER_ID, COUPON);

        verify(couponServiceClient).calculateCouponDiscount(any(CouponCalculateRequest.class));
        verify(couponServiceClient, never()).calculateAndRedeemCouponDiscount(any(CouponRedeemRequest.class));

        assertThat("The total price calculated with discount should be 1500", result.getTotalPrice(), is(2000D));
        assertThat("The total price without discount should be 2000", result.getTotalPriceWithoutDiscount(), is(2000D));
        assertThat("The discount should be 0", result.getDiscount(), is(0d));
        assertThat("The coupon amount should be 5", result.getCouponAmount(), is(0d));
        assertThat("Coupon should be valid", result.getCouponValid(), is(false));
    }

    @Test
    public void testCalculateOrderPriceWithCoupon() {
        PriceCalculation result =  productsPriceCalculatorServiceImpl.calculateOrderPriceWithCoupon(getProductsRequests(), ORDER_ID, COUPON);

        verify(couponServiceClient, never()).calculateCouponDiscount(any(CouponCalculateRequest.class));
        verify(couponServiceClient).calculateAndRedeemCouponDiscount(any(CouponRedeemRequest.class));

        assertThat("The total price calculated with discount should be 2000", result.getTotalPrice(), is(1500D));
        assertThat("The total price without discount should be 2000", result.getTotalPriceWithoutDiscount(), is(2000D));
        assertThat("The discount should be 0", result.getDiscount(), is(500D));
        assertThat("The coupon amount should be 0", result.getCouponAmount(), is(5D));
        assertThat("Coupon should be valid", result.getCouponValid(), is(true));
    }

    @Test
    public void testCalculateOrderPriceWithCouponInvalid() {
        when(couponServiceClient.calculateAndRedeemCouponDiscount(any(CouponRedeemRequest.class))).thenThrow(new InvalidCouponException("Invalid coupon"));

        PriceCalculation result =  productsPriceCalculatorServiceImpl.calculateOrderPriceWithCoupon(getProductsRequests(), ORDER_ID, COUPON);

        verify(couponServiceClient, never()).calculateCouponDiscount(any(CouponCalculateRequest.class));
        verify(couponServiceClient).calculateAndRedeemCouponDiscount(any(CouponRedeemRequest.class));

        assertThat("The total price calculated with discount should be 2000", result.getTotalPrice(), is(2000D));
        assertThat("The total price without discount should be 2000", result.getTotalPriceWithoutDiscount(), is(2000D));
        assertThat("The discount should be 0", result.getDiscount(), is(0d));
        assertThat("The coupon amount should be 0", result.getCouponAmount(), is(0d));
        assertThat("Coupon should be valid", result.getCouponValid(), is(false));
    }

}
