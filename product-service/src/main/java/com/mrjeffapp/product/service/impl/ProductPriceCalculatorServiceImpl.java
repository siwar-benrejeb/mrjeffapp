package com.mrjeffapp.product.service.impl;

import com.google.common.collect.Sets;
import com.mrjeffapp.product.api.dto.ProductRequest;
import com.mrjeffapp.product.client.CouponServiceClient;
import com.mrjeffapp.product.client.coupon.model.*;
import com.mrjeffapp.product.domain.PriceCalculation;
import com.mrjeffapp.product.domain.Product;
import com.mrjeffapp.product.exception.CustomerNotFoundCoupon;
import com.mrjeffapp.product.exception.InvalidCouponException;
import com.mrjeffapp.product.exception.ProductNotFoundException;
import com.mrjeffapp.product.repository.ProductRepository;
import com.mrjeffapp.product.service.ProductPriceCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductPriceCalculatorServiceImpl implements ProductPriceCalculatorService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductPriceCalculatorServiceImpl.class);

    private static final boolean COUPON_VALID = true;
    private static final boolean COUPON_INVALID = false;

    private static final String JOIN_DELIMITER = ",";

    private static final int TOTAL_DECIMALS = 2;
    private static final int PERCENTAGE = 100;

    private static final String NO_COUPON_ID = null;
    private static final String NO_COUPON = null;
    private static final Double NO_COUPON_AMOUNT = null;
    private static final double NO_DISCOUNT = 0;
    private static final String NO_DISCOUNT_CATEGORY = null;
    private static final Boolean NO_COUPON_VALID = null;

    private final ProductRepository productRepository;

    private final CouponServiceClient couponServiceClient;

    @Autowired
    public ProductPriceCalculatorServiceImpl(ProductRepository productRepository, CouponServiceClient couponServiceClient) {
        this.productRepository = productRepository;
        this.couponServiceClient = couponServiceClient;
    }

    @Override
    public PriceCalculation calculatePrice(List<ProductRequest> productsRequest, String customerId) {
        double totalAmount = calculateOrderTotalAmount(productsRequest);

        PriceCalculation result = new PriceCalculation(totalAmount, totalAmount, NO_DISCOUNT, NO_DISCOUNT_CATEGORY, NO_COUPON_ID, NO_COUPON, NO_COUPON_AMOUNT, NO_COUPON_VALID);
        return result;
    }

    @Override
    public PriceCalculation calculateCartPriceWithCoupon(List<ProductRequest> productsRequest, String customerId, String coupon) {
        validateCalculateCartPriceWithCoupon(customerId);

        double cartAmount = calculateOrderTotalAmount(productsRequest);
        LOG.debug("Total order amount from coupon: {}", cartAmount);

        CouponCalculateRequest request = createCouponCalculateRequest(productsRequest, cartAmount, customerId, coupon);

        Double discountAmount;
        String discountCategory = null;
        String couponId = null;
        String couponCode = null;
        Double couponAmount = null;
        Boolean couponValid;
        try {
            CouponCalculateResponse response = couponServiceClient.calculateCouponDiscount(request);
            discountAmount = response.getOrderAmountDiscount();
            discountCategory = response.getDiscountCategory();
            couponId = response.getCouponId();
            couponCode = response.getCoupon();
            couponAmount = response.getCouponAmount();

            couponValid = true;

        } catch (InvalidCouponException e) {
            discountAmount = 0d;
            couponAmount = 0d;

            couponValid = false;
        }

        LOG.debug("Discount amount from coupon: {}", discountAmount);

        double finalOrderAmount = subtract(cartAmount, discountAmount);

        PriceCalculation result = new PriceCalculation(finalOrderAmount,
                                                            cartAmount,
                                                            discountAmount,
                                                            discountCategory,
                                                            couponId,
                                                            couponCode,
                                                            couponAmount,
                                                            couponValid);

        return result;
    }

    private void validateCalculateCartPriceWithCoupon(String customerId) {
        if(customerId == null) {
            throw new CustomerNotFoundCoupon("Customer not set when using a coupon");
        }
    }

    @Override
    public PriceCalculation calculateOrderPriceWithCoupon(List<ProductRequest> productsRequest, String customerId, String coupon) {
        validateCalculateCartPriceWithCoupon(customerId);

        double orderAmount = calculateOrderTotalAmount(productsRequest);

        LOG.debug("Total order amount from coupon: {}", orderAmount);

        CouponRedeemRequest request = createCouponRedeemRequest(productsRequest, orderAmount, customerId, coupon);

        Double discountAmount;
        String discountCategory = null;
        String couponId = null;
        String couponCode = null;
        Double couponAmount = null;
        Boolean couponValid;

        try {
            CouponRedeemResponse response = couponServiceClient.calculateAndRedeemCouponDiscount(request);
            discountAmount = response.getOrderAmountDiscount();
            discountCategory = response.getDiscountCategory();
            couponId = response.getCouponId();
            couponCode = response.getCoupon();
            couponAmount = response.getCouponAmount();

            couponValid = true;

        } catch (InvalidCouponException ex) {
            discountAmount = 0d;
            couponAmount = 0d;
            couponValid = false;

        }

        LOG.debug("Discount amount from coupon: {}", discountAmount);

        double finalOrderAmount = subtract(orderAmount, discountAmount);

        PriceCalculation result = new PriceCalculation(finalOrderAmount,
                                                            orderAmount,
                                                            discountAmount,
                                                            discountCategory,
                                                            couponId,
                                                            couponCode,
                                                            couponAmount,
                                                            couponValid);
        return result;
    }

    private double subtract(double amount, double discount) {
        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        BigDecimal bigDecimalDiscountAmount = new BigDecimal(discount);

        BigDecimal bigDecimalFinalOrderAmount = bigDecimalAmount.subtract(bigDecimalDiscountAmount);

        double finalOrderAmount = bigDecimalFinalOrderAmount.setScale(TOTAL_DECIMALS, RoundingMode.HALF_UP).doubleValue();
        LOG.debug("Final order amount: {}", finalOrderAmount);

        return finalOrderAmount;
    }

    private double calculateOrderTotalAmount(List<ProductRequest> productsRequest) {
        Set<String> productsCodes = productsRequest.stream()
                                        .map(p -> p.getCode().toUpperCase())
                                        .collect(Collectors.toSet());

        LOG.debug("Products codes: {}", productsCodes);

        Collection<Product> products = productRepository.findByCodeInAndActiveTrue(productsCodes);

        Set<String> productsCodesFound = products.stream()
                        .map(p -> p.getCode().toUpperCase())
                        .collect(Collectors.toSet());

        validateProductsAvailable(productsCodes, productsCodesFound);

        LOG.debug("Products found: {}", products.size());

        Map<String, Product> mapProductCode = products.stream()
                .collect(Collectors.toMap(p -> p.getCode().toUpperCase(), Function.identity()));

        double totalAmount = calculateTotalProducts(productsRequest, mapProductCode);

        LOG.debug("Total amount calculated: {}", totalAmount);

        BigDecimal bigDecimal = new BigDecimal(totalAmount).setScale(TOTAL_DECIMALS, RoundingMode.HALF_UP);
        double totalAmountRounded = bigDecimal.doubleValue();

        LOG.debug("Total amount rounded: {}", totalAmountRounded);
        return totalAmountRounded;
    }

    private Set<CouponProductAmount> getCouponProductAmounts(List<ProductRequest> productsRequest) {
        Set<CouponProductAmount> couponProductAmounts = new HashSet<>();
        Set<String> productsCodes = productsRequest.stream()
                                        .map(p -> p.getCode().toUpperCase())
                                        .collect(Collectors.toSet());

        LOG.debug("Products codes: {}", productsCodes);

        Collection<Product> products = productRepository.findByCodeInAndActiveTrue(productsCodes);

        Map<String, Integer> mapProductCode = productsRequest.stream()
                                    .collect(Collectors.toMap(p -> p.getCode().toUpperCase(), ProductRequest::getQuantity));

        for(Product product : products) {
            int quantity = mapProductCode.get(product.getCode().toUpperCase());

            CouponProductAmount couponProductAmount = new CouponProductAmount();
            couponProductAmount.setProductId(product.getId());

            double totalAmountProduct = product.getPrice() * quantity;
            couponProductAmount.setTotalAmount(totalAmountProduct);

            LOG.debug("CouponProductAmount: {}", couponProductAmount);
            couponProductAmounts.add(couponProductAmount);
        }
        return couponProductAmounts;
    }

    private CouponCalculateRequest createCouponCalculateRequest(List<ProductRequest> productsRequest, double totalAmount, String customerId, String coupon) {
        Set<CouponProductAmount> couponProductAmounts = getCouponProductAmounts(productsRequest);

        CouponCalculateRequest couponCalculateRequest = new CouponCalculateRequest();
        couponCalculateRequest.setOrderAmount(totalAmount);
        couponCalculateRequest.setProductsAmount(couponProductAmounts);
        couponCalculateRequest.setCoupon(coupon);
        couponCalculateRequest.setCustomerId(customerId);

        return couponCalculateRequest;
    }

    private CouponRedeemRequest createCouponRedeemRequest(List<ProductRequest> productsRequest, double totalAmount, String customerId, String coupon) {
        Set<CouponProductAmount> couponProductAmounts = getCouponProductAmounts(productsRequest);

        CouponRedeemRequest couponRedeemRequest = new CouponRedeemRequest();
        couponRedeemRequest.setOrderAmount(totalAmount);
        couponRedeemRequest.setProductsAmount(couponProductAmounts);
        couponRedeemRequest.setCoupon(coupon);
        couponRedeemRequest.setCustomerId(customerId);

        return couponRedeemRequest;
    }

    private void validateProductsAvailable(Set<String> productsCodes, Set<String> productsCodesFound) {
        if(productsCodes.size() != productsCodesFound.size()) {
            String productsNotFound = Sets.difference(productsCodes, productsCodesFound).stream()
                                                .collect(Collectors.joining(JOIN_DELIMITER));

            String message = "Products with code not found: " + productsNotFound;
            LOG.warn(message);
            throw new ProductNotFoundException(message);
        }
    }

    private double calculateTotalProducts(List<ProductRequest> productsRequest, Map<String, Product> mapProductCode) {
        double totalAmount = 0;
        for(ProductRequest productRequest : productsRequest) {
            double currentProductPrice = calculateTotalProduct(productRequest, mapProductCode);

            totalAmount += currentProductPrice;
            LOG.debug("Total amount calculated: {}", totalAmount);
        }
        return totalAmount;
    }

    private double calculateTotalProduct(ProductRequest productRequest, Map<String, Product> mapProductCode) {
        Product product = mapProductCode.get(productRequest.getCode().toUpperCase());

        LOG.debug("Products: {}, quantity: {}, unit price {}", productRequest.getCode(), productRequest.getQuantity(), product.getPriceWithoutVat());

        double currentProductPrice = product.getPrice() * productRequest.getQuantity();
        LOG.debug("Current price: {}", currentProductPrice);

        return currentProductPrice;
    }

}
