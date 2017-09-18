package com.mrjeffapp.provider.client.order;

import com.mrjeffapp.provider.client.order.model.Address;
import com.mrjeffapp.provider.client.order.model.CheckoutOrderProductRequest;
import com.mrjeffapp.provider.client.order.model.Order;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(name = "order-service", decode404 = true)
public interface OrderServiceClient {

    @RequestMapping(method = GET, value = "/api/orders/{id}")
    Resource<Order> findOne(@PathVariable("id") String orderId);

    @RequestMapping(method = GET, value = "/api/orders/{id}")
    Resource<Order> findOne(@PathVariable("id") String orderId, @RequestParam("projection") String projection);

    @RequestMapping(method = GET, value = "/api/orders/search/findByCustomerIdIn")
    PagedResources<Order> findOrdersByCustomerIdIn(@RequestParam("ids") String customersId, @RequestParam("sort") String sort, @RequestParam("projection") String projection, @RequestParam("size") Integer size);

    @RequestMapping(method = GET, value = "/api/orders/search/findByCustomerId")
    PagedResources<Order> findOrdersByCustomerId(@RequestParam("id") String customerId, @RequestParam("sort") String sort, @RequestParam("projection") String projection, @RequestParam("size") Integer size);

    @RequestMapping(method = GET, value = "/api/orders/search/findByIdIn")
    PagedResources<Order> findOrdersByIdIn(@RequestParam("ids") String ordersId, @RequestParam("projection") String projection);

    @RequestMapping(method = GET, value = "/api/orders/search/findByCodeIn")
    PagedResources<Order> findOrdersByCodeIn(@RequestParam("codes") String ordersCode, @RequestParam("projection") String projection);

    @RequestMapping(method = GET, value = "/api/orders/search/findDailyOrderByStatus")
    PagedResources<Order> findDailyOrderByStatus(@RequestParam("headquarterId") String headquarterId,
                                                 @RequestParam("orderStatusId") String orderStatusId,
                                                 @RequestParam("date") Date date,
                                                 @RequestParam("projection") String projection,
                                                 @RequestParam("page") int page,
                                                 @RequestParam("size") int size);

    @RequestMapping(method = GET, value = "/api/orders/search/findProviderDailyOrderByStatus")
    PagedResources<Order> findProviderDailyOrderByStatus(@RequestParam("providerId") String providerId,
                                                         @RequestParam("orderStatusId") String orderStatusId,
                                                         @RequestParam("date") Date date,
                                                         @RequestParam("projection") String projection,
                                                         @RequestParam("page") int page,
                                                         @RequestParam("size") int size);

    @RequestMapping(method = GET, value = "/api/orders/search/findDailyOrderByVisitType")
    PagedResources<Order> findDailyOrderByVisitType(@RequestParam("headquarterId") String headquarterId,
                                                    @RequestParam("visitTypeCode") String visitTypeCode,
                                                    @RequestParam("date") Date date,
                                                    @RequestParam("sort") String orderSort,
                                                    @RequestParam("projection") String projection,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size);

    @RequestMapping(method = GET, value = "/api/orders/search/findDailyOrderByTimeRangeVisitTypesAndStatuses")
    PagedResources<Order> findDailyOrderByTimeRangeVisitTypesAndStatuses(@RequestParam("headquartersId") String headquartersId,
                                                                         @RequestParam("visitTypeCodes") String visitTypesCode,
                                                                         @RequestParam("orderStatusCodes") String orderStatusCodes,
                                                                         @RequestParam("date") Date date,
                                                                         @RequestParam("startTime") String startTime,
                                                                         @RequestParam("endTime") String endTime,
                                                                         @RequestParam("sort") String orderSort,
                                                                         @RequestParam("projection") String projection,
                                                                         @RequestParam("page") int page,
                                                                         @RequestParam("size") int size);

    @RequestMapping(method = GET, value = "/api/addresses/search/findByIdIn")
    PagedResources<Address> findAddressByIdIn(@RequestParam("ids") String ids, @RequestParam("size") Integer size);

    @RequestMapping(method = POST, value = "order-service/v1/checkout/orders/{orderId}/products")
    PagedResources<Order> checkOutOrder(@PathVariable("orderId") String orderId,
                                        @RequestParam("products") CheckoutOrderProductRequest checkoutOrderProductRequests);

}
