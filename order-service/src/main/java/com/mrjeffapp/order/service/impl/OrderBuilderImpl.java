package com.mrjeffapp.order.service.impl;

import com.mrjeffapp.order.api.dto.OrderCreateProductRequest;
import com.mrjeffapp.order.api.dto.OrderCreateRequest;
import com.mrjeffapp.order.api.dto.OrderCreateVisitRequest;
import com.mrjeffapp.order.client.administrative.model.TimeTableAvailableResponse;
import com.mrjeffapp.order.client.customer.CustomerClient;
import com.mrjeffapp.order.client.customer.model.Address;
import com.mrjeffapp.order.client.product.ProductClient;
import com.mrjeffapp.order.client.product.model.OrderCalculatorProductRequest;
import com.mrjeffapp.order.client.product.model.OrderCalculatorRequest;
import com.mrjeffapp.order.client.product.model.OrderCalculatorResponse;
import com.mrjeffapp.order.client.product.model.Product;
import com.mrjeffapp.order.domain.*;
import com.mrjeffapp.order.exception.*;
import com.mrjeffapp.order.repository.ChannelRepository;
import com.mrjeffapp.order.repository.HeadquarterAssignmentConfigurationRepository;
import com.mrjeffapp.order.repository.OrderStatusRepository;
import com.mrjeffapp.order.repository.OrderTypeRepository;
import com.mrjeffapp.order.service.OrderBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

import static com.mrjeffapp.order.config.Constants.*;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang.time.DateUtils.truncate;

@Component
public class OrderBuilderImpl implements OrderBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(OrderBuilderImpl.class);
    private static final String ITEM_DELIMITER = ",";
    private static final int VISIT_COUNT_EXPECTED = 2;

    private final HeadquarterAssignmentConfigurationRepository headquarterAssignmentConfigurationRepository;

    private final OrderStatusRepository orderStatusRepository;

    private final ChannelRepository channelRepository;

    private final OrderTypeRepository orderTypeRepository;

    private final ProductClient productClient;

    private final CustomerClient customerClient;


    @Autowired
    public OrderBuilderImpl(HeadquarterAssignmentConfigurationRepository headquarterAssignmentConfigurationRepository, OrderStatusRepository orderStatusRepository, ChannelRepository channelRepository, OrderTypeRepository orderTypeRepository, ProductClient productClient, CustomerClient customerClient) {
        this.headquarterAssignmentConfigurationRepository = headquarterAssignmentConfigurationRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.channelRepository = channelRepository;
        this.orderTypeRepository = orderTypeRepository;
        this.productClient = productClient;
        this.customerClient = customerClient;
    }

    @Override
    public Order buildOrder(OrderCreateRequest orderRequest) {
        LOG.debug("Creating order from request: {}", orderRequest);

        String uuid = UUID.randomUUID().toString();
        LOG.debug("Creating order with id: {}", uuid);

        Order order = new Order();
        order.setId(uuid);
        order.setCode(uuid);
        order.setOrderDate(truncate(new Date(), Calendar.DAY_OF_MONTH));
        order.setCustomerId(orderRequest.getCustomerId());
        order.setNote(orderRequest.getNote());
        order.setBillingAddressId(orderRequest.getBillingAddressId());

        order.setPaymentMethodCode(orderRequest.getPaymentMethodCode());

//        LOG.debug("Assigning order status");
//        OrderStatus orderStatusCreated = orderStatusRepository.findByCodeAndActiveTrue(ORDER_STATUS_CREATED).get();
//        order.setOrderStatus(orderStatusCreated);

        LOG.debug("Assigning visits");
        setOrderLocationAndVisits(orderRequest, order);

        LOG.debug("Assigning headquarter id");
        setHeadquarterByPostalCode(orderRequest, order);

//        LOG.debug("Assigning order type with value: {}", orderRequest.getOrderTypeCode());
//        setOrderType(orderRequest, order);

        LOG.debug("Assigning channel");
        setChannel(orderRequest, order);

        LOG.debug("Assigning products to order");
        setProducts(orderRequest, order);

        LOG.debug("Assigning total price");
        setPrice(orderRequest, order);

        return order;
    }

    private void setOrderType(OrderCreateRequest orderRequest, Order order) {
        String orderTypeCode = orderRequest.getOrderTypeCode();

        Optional<OrderType> orderTypeOptional = orderTypeRepository.findByCodeAndActiveTrue(orderTypeCode);
        if(!orderTypeOptional.isPresent()) {
            throw new InvalidOrderTypeException("Invalid order type with code: " + orderTypeCode);
        }

        order.setOrderType(orderTypeOptional.get());
    }

    private void setChannel(OrderCreateRequest orderRequest, Order order) {
        Optional<Channel> channelOptional = channelRepository.findByCodeAndActiveTrue(orderRequest.getChannelCode());

        if(!channelOptional.isPresent()) {
            throw new InvalidChannelException("Not found channel with code: " + orderRequest.getChannelCode());
        }

        order.setChannel(channelOptional.get());
    }

    private void setHeadquarterByPostalCode(OrderCreateRequest orderCreateRequest, Order order) {
        String postalCodeId = order.getPostalCodeId();

        LOG.debug("Assigning headquarter for postalCodeId: {}", postalCodeId);
        HeadquarterAssignmentConfiguration configuration = headquarterAssignmentConfigurationRepository.findByPostalCodeIdAndActiveTrue(postalCodeId);

        String headquarterId;
        if(configuration == null) {
            LOG.error("Provider not assigned to postal code: {}", postalCodeId);
            headquarterId = null;

        } else {
            headquarterId = configuration.getHeadquarterId();

        }

        LOG.debug("Assigned headquarter for postalCodeId: {}, headquarterId: {}", postalCodeId, headquarterId);

        order.setHeadquarterId(headquarterId);
    }

    private void setProducts(OrderCreateRequest orderRequest, Order order) {
        Set<OrderCreateProductRequest> orderCreatedProduct = orderRequest.getProducts();

        String productCodes = orderRequest.getProducts().stream()
                                    .map(OrderCreateProductRequest::getCode)
                                    .map(String::toUpperCase)
                                    .collect(joining(ITEM_DELIMITER));
        LOG.debug("Product codes: {}", productCodes);

        Resources<Product> productResources = productClient.findProductsByIdInProducts(productCodes);

      checkProducts(orderRequest, productResources);

        Collection<Product> productsCodesUppercase = productResources.getContent();
        for(Product product : productsCodesUppercase) {
            product.setCode(product.getCode().toUpperCase());
        }
        LOG.debug("Product codes uppercase: {}", productsCodesUppercase);

        Map<String, Product> mapCodeProduct = productsCodesUppercase.stream()
                                                    .collect(toMap(Product::getCode, product -> product));

        LOG.debug("Total products retrieved: {}", mapCodeProduct.size());

        Set<ProductOrder> productsOrder = new HashSet<>();
        for(OrderCreateProductRequest orderProductRequest : orderCreatedProduct) {
            String productCode = orderProductRequest.getCode().toUpperCase();
            Product product = mapCodeProduct.get(productCode);

            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrder(order);
            productOrder.setProductId(product.getId());
            productOrder.setQuantity(orderProductRequest.getQuantity());

            LOG.debug("Assigning to order product productId: {}", product.getId());
            productsOrder.add(productOrder);
        }

        order.setProductsOrder(productsOrder);
    }

    private void checkProducts(OrderCreateRequest orderRequest, Resources<Product> productResources) {
        Set<String> expectedProductCodes = orderRequest.getProducts().stream()
                                                        .map(p -> p.getCode())
                                                        .map(c -> c.toLowerCase())
                                                        .collect(toSet());

        if(productResources == null) {
            throw new InvalidProductException("Not found any product with codes: " + expectedProductCodes);
        }

        Set<String> productCodes = productResources.getContent().stream()
                                            .map(p -> p.getCode())
                                            .map(c -> c.toLowerCase())
                                            .collect(toSet());

        if(!productCodes.containsAll(expectedProductCodes)) {
            throw new InvalidProductException("Products expected=" + expectedProductCodes + ", actual=" + productCodes);
        }
    }

    private void setOrderLocationAndVisits(OrderCreateRequest orderRequest, Order order) {
        validateVisitTypes(orderRequest);

        Map<String, OrderCreateVisitRequest> visitTypeCodeAddressRequest = orderRequest.getVisits().stream()
                                                .collect(toMap(OrderCreateVisitRequest::getVisitTypeCode, visit -> visit));

        Map<String, Address> mapVisitTypeAddress = getVisitAddressMap(orderRequest);

        setOrderCountryFromPickupAddress(order, mapVisitTypeAddress);

        setOrderPostalCodeFromPickupAddress(order, mapVisitTypeAddress);

        Visit visitPickup = getVisit(VISIT_TYPE_CODE_PICKUP, orderRequest.getTimetableTypeCode(), mapVisitTypeAddress, visitTypeCodeAddressRequest);
        visitPickup.setOrder(order);

        Visit visitDelivery = getVisit(VISIT_TYPE_CODE_DELIVERY, orderRequest.getTimetableTypeCode(), mapVisitTypeAddress, visitTypeCodeAddressRequest);
        visitDelivery.setOrder(order);

        String pickupTimeSlotCode = visitTypeCodeAddressRequest.get(VISIT_TYPE_CODE_PICKUP).getTimeSlotCode();
        validateVisitsTiming(orderRequest, visitPickup, visitDelivery);

        String visitPickupCity = visitPickup.getCityId();
        String visitDeliveryCity = visitDelivery.getCityId();
//        if(!visitPickupCity.equals(visitDeliveryCity)) {
//            throw new InvalidVisitCityException(String.format("Pickup city and delivery city must be the same: pickup: %s, delivery: %s", visitPickupCity, visitDeliveryCity));
//        }

        order.setVisits(Stream.of(visitPickup, visitDelivery).collect(toSet()));
    }

    private void validateVisitTypes(OrderCreateRequest orderRequest) {
        int visitCount = orderRequest.getVisits().size();
        if(visitCount != VISIT_COUNT_EXPECTED) {
            throw new InvalidVisitCountException("Invalid visit count, expected: " + VISIT_COUNT_EXPECTED + ", actual="+visitCount);
        }

        Set<String> expectedVisitTypes = Stream.of(VISIT_TYPE_CODE_PICKUP, VISIT_TYPE_CODE_DELIVERY).collect(toSet());

        Set<String> visitTypes = orderRequest.getVisits().stream()
                                                    .map(v -> v.getVisitTypeCode())
                                                    .collect(toSet());

        if(!visitTypes.containsAll(expectedVisitTypes)) {
            throw new MissingVisitTypeException("VisitTypes expected=" + expectedVisitTypes + ", found=" + visitTypes);
        }

    }

    private Visit getVisit(String visitTypeCode, String timeTableTypeCode, Map<String, Address> mapVisitTypeAddress, Map<String, OrderCreateVisitRequest> visitTypeCodeAddressRequest) {
        if(!mapVisitTypeAddress.containsKey(visitTypeCode)) {
            throw new InvalidVisitTypeException("Not found address for visit type: " + visitTypeCode);
        }

        Address address = mapVisitTypeAddress.get(visitTypeCode);
        OrderCreateVisitRequest orderCreateVisitRequest = visitTypeCodeAddressRequest.get(visitTypeCode);

        Visit visit = new Visit();
        visit.setDate(orderCreateVisitRequest.getDate());
        visit.setAddressId(address.getId());
        visit.setVisitTypeCode(visitTypeCode);
        visit.setPostalCodeId(address.getPostalCodeId());
        visit.setPostalCode(address.getPostalCode());
        visit.setCountryId(address.getCountryId());
        visit.setCityId(address.getCityId());

        LOG.debug("AdministrativeClient.timeTableAvailability, timeTableTypeCode={}", timeTableTypeCode);

//        TimeTableAvailableResponse response = administrativeClient.timeTableAvailability(timeTableTypeCode,
//                                                                        address.getPostalCodeId(),
//                                                                        orderCreateVisitRequest.getDate(),
//                                                                        visitTypeCode,
//                                                                        orderCreateVisitRequest.getTimeSlotCode());
//
//        LOG.debug("AdministrativeClient.timeTableAvailability, response={}", response);

        boolean timeSlotAvailable = true;
        if(!timeSlotAvailable) {
            String message = String.format("Time slot not available orderTypeCode=%s, postalCodeId=%s, date=%s, visitType=%s, timeRangeCode=%s", timeTableTypeCode, address.getPostalCodeId(), orderCreateVisitRequest.getDate(), visitTypeCode, orderCreateVisitRequest.getTimeSlotCode());
            throw new InvalidTimeSlotException(message);
        }

        visit.setTimeSlotStart(visit.getTimeSlotStart());
        visit.setTimeSlotEnd(visit.getTimeSlotEnd());
        //visit.setTimeTableTimeSlotId(response.getTimetableTimeSlotId());

        LOG.debug("Creating type: {} values: {}", visitTypeCode, visit);
        return visit;
    }

    private void validateVisitsTiming(OrderCreateRequest orderCreateRequest, Visit visitPickup, Visit visitDelivery) {
        Date visitPickupDate = visitPickup.getDate();
        Date visitDeliveryDate = visitDelivery.getDate();
        OrderCreateVisitRequest orderCreateVisitRequest = orderCreateRequest.getDeliveryVisitRequest();

        LOG.debug("validateVisitsTiming request: timeTableTypeCode={}, postalCodeId={}, date={}, visitTypeCode={}, timeSlotCode={}", orderCreateRequest.getTimetableTypeCode(), visitPickup.getPostalCodeId(), visitPickup.getDate(), visitPickup.getVisitTypeCode(), orderCreateVisitRequest.getTimeSlotCode());

//        TimeTableAvailableResponse response = administrativeClient.timeTableAvailability(orderCreateRequest.getTimetableTypeCode(),
//                                                                                            visitPickup.getPostalCodeId(),
//                                                                                            orderCreateVisitRequest.getDate(),
//                                                                                            VISIT_TYPE_CODE_PICKUP,
//                                                                                            orderCreateVisitRequest.getTimeSlotCode());
//
//        LOG.debug("validateVisitsTiming response: {}", response);

        boolean timeSlotAvailable = true;
        if(!timeSlotAvailable) {
            String message = String.format("Time slot not available timeTableTypeCode=%s, postalCodeId=%s, date=%s, visitType=%s, timeRangeCode=%s", orderCreateRequest.getTimetableTypeCode(), visitPickup.getPostalCodeId(), orderCreateVisitRequest.getDate(), visitPickup.getVisitTypeCode(), orderCreateVisitRequest.getTimeSlotCode());
            throw new InvalidTimeSlotException(message);
        }

        int timingDaysBetweenVisits = 3;
        Date minimumValidDate = DateUtils.addDays(visitPickupDate, timingDaysBetweenVisits);

        boolean validDate = minimumValidDate.equals(visitDeliveryDate) || minimumValidDate.before(visitDeliveryDate);
        if(!validDate) {
            String message = String.format("pickup=%s, delivery=%s, minimumDelivery=%s", visitPickupDate, visitDeliveryDate, minimumValidDate);
            throw new InvalidTimingDateException(message);
        }

    }

    private Map<String, Address> getVisitAddressMap(OrderCreateRequest orderRequest) {
        Map<String, String> mapVisitTypeAddressId = orderRequest.getVisits().stream()
                                                    .collect(toMap(OrderCreateVisitRequest::getVisitTypeCode, OrderCreateVisitRequest::getAddressId));
        LOG.debug("Visit by type and id: {}", mapVisitTypeAddressId);

        Set<String> addressesId = orderRequest.getVisits().stream()
                .map(OrderCreateVisitRequest::getAddressId)
                .collect(toSet());

        String visitAddressesId = addressesId.stream()
                .map(Object::toString)
                .collect(joining(ITEM_DELIMITER));
        LOG.debug("Visits ids: {}", visitAddressesId);

        String customerId = orderRequest.getCustomerId();
        Resources<Address> addresses = customerClient.findAddressesByCustomerIdAndIdInAndActiveTrue(customerId, visitAddressesId);

        checkCustomerAddressesRegistered(customerId, addressesId, addresses);

        Map<String, Address> mapIdAddress = addresses.getContent().stream()
                                                        .collect(toMap(Address::getId, address -> address));

        Map<String, Address> mapVisitTypeAddress = new HashMap<>();

        mapVisitTypeAddressId.forEach((visitTypeCode , addressId) -> {
            mapVisitTypeAddress.put(visitTypeCode.toUpperCase(), mapIdAddress.get(addressId));
        });
        return mapVisitTypeAddress;
    }

    private void checkCustomerAddressesRegistered(String customerId, Set<String> expectedAddressesId, Resources<Address> addressesResources) {
        if(addressesResources == null) {
            String message = "Not found any addresses for customer:" + customerId;
            LOG.warn(message);

            throw new InvalidCustomerException(message);
        }

        Collection<Address> addresses = addressesResources.getContent();
        Set<String> addressesId = addresses.stream()
                                            .map(address -> address.getId())
                                            .collect(toSet());

        String message = "Addresses found: " + addressesId + " expected: " + expectedAddressesId;
        LOG.debug(message);

        if(!expectedAddressesId.equals(addressesId)) {
            LOG.warn(message);
            throw new InvalidAddressException(message);
        }

    }

    private void setOrderCountryFromPickupAddress(Order order, Map<String, Address> mapVisitTypeAddress) {
        Address pickupAddress = mapVisitTypeAddress.get(VISIT_TYPE_CODE_PICKUP);
        String orderCountryCodeId = pickupAddress.getCountryId();
        String orderCountryCode = pickupAddress.getCountryCode();

        LOG.debug("Country id from PICKUP address: addressId:{} countryId:{}, countryCode:{}", pickupAddress.getId(), orderCountryCodeId, orderCountryCode);
        order.setCountryId(orderCountryCodeId);
        order.setCountryCode(orderCountryCode);
    }

    private void setOrderPostalCodeFromPickupAddress(Order order, Map<String, Address> mapVisitTypeAddress) {
        Address pickupAddress = mapVisitTypeAddress.get(VISIT_TYPE_CODE_PICKUP);
        String orderPostalCodeId = pickupAddress.getPostalCodeId();

        LOG.debug("Order postal code id from PICKUP address: addressId:{} postalCodeId:{}", pickupAddress.getId(), orderPostalCodeId);
        order.setPostalCodeId(orderPostalCodeId);
    }

    private void setPrice(OrderCreateRequest orderRequest, Order order) {
        OrderCalculatorRequest request = buildOrderCalculatorRequest(orderRequest);
        LOG.debug("OrderCalculatorRequest: {}", request);

        OrderCalculatorResponse response = productClient.calculateOrderTotal(request);

        LOG.debug("OrderCalculatorResponse: {}", response);

        if(orderRequest.hasCouponCode()) {
            boolean validCoupon = response.getCouponValid();
            if (!validCoupon) {
                throw new InvalidCouponException("Coupon not valid, coupon:  " + request.getCoupon());
            }
        }

        order.setTotalPrice(response.getTotalPrice());
        order.setTotalPriceWithoutDiscount(response.getTotalPriceWithoutDiscount());
        order.setTotalPriceDiscount(response.getDiscount());
        order.setCouponId(response.getCouponId());
    }

    private OrderCalculatorRequest buildOrderCalculatorRequest(OrderCreateRequest orderRequest) {
        OrderCalculatorRequest request = new OrderCalculatorRequest();
        request.setCustomerId(orderRequest.getCustomerId());
        request.setCoupon(orderRequest.getCouponCode());

        List<OrderCalculatorProductRequest> products = new ArrayList<>();
        for(OrderCreateProductRequest orderCreateProduct : orderRequest.getProducts()) {
            OrderCalculatorProductRequest productRequest = new OrderCalculatorProductRequest();
            productRequest.setCode(orderCreateProduct.getCode());
            productRequest.setQuantity(orderCreateProduct.getQuantity());

            products.add(productRequest);
        }

        request.setProducts(products);

        return request;
    }

}
