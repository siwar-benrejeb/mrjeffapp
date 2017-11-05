//package com.mrjeffapp.order.service.impl;
//
//import com.mrjeffapp.order.client.administrative.AdministrativeClient;
//import com.mrjeffapp.order.client.administrative.model.TimeTableAvailableResponse;
//import com.mrjeffapp.order.client.customer.CustomerClient;
//import com.mrjeffapp.order.client.customer.model.Address;
//import com.mrjeffapp.order.exception.InvalidAddressException;
//import com.mrjeffapp.order.exception.InvalidTimeSlotException;
//import com.mrjeffapp.order.exception.InvalidVisitException;
//import com.mrjeffapp.order.domain.Order;
//import com.mrjeffapp.order.domain.OrderType;
//import com.mrjeffapp.order.domain.Visit;
//import com.mrjeffapp.order.repository.VisitRepository;
//import com.mrjeffapp.order.service.EventPublisherService;
//import org.apache.commons.lang.time.DateUtils;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.hateoas.PagedResources;
//
//import java.sql.Time;
//import java.util.Date;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//import static java.util.stream.Collectors.toSet;
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertThat;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class VisitServiceTest {
//
////    private static final String VISIT_ID = "1L";
////    private static final String CUSTOMER_ID = "2L";
////    private static final String ORDER_TYPE_CODE = "B2B";
////    private static final String VISIT_TYPE_CODE = "PICKUP";
////    private static final String RELOCATION_TIME_SLOT_CODE = "10:00-11:00";
////
////    private static final String ADDRESS_ID = "3L";
////    private static final String ADDRESS_POSTAL_CODE = "46000";
////    private static final String ADDRESS_POSTAL_CODE_ID = "4L";
////    private static final String ADDRESS_CITY_ID = "5L";
////    private static final String ADDRESS_COUNTRY_ID = "6L";
////
////    private static final String VISIT_POSTAL_CODE_ID = "1L";
////
////    private static final String TIME_TABLE_TIME_SLOT_ID = "5L";
////    private static final Time TIME_TABLE_TIME_SLOT_START = Time.valueOf("10:00:00");
////    private static final Time TIME_TABLE_TIME_SLOT_END = Time.valueOf("11:00:00");
////
////    @Mock
////    private CustomerClient customerClient;
////
//////    @Mock
//////    private AdministrativeClient administrativeClient;
//////
////    @Mock
////    private VisitRepository visitRepository;
////
////    @Mock
////    private EventPublisherService eventPublisherService;
////
////    @InjectMocks
////    private VisitServiceImpl visitServiceImpl;
////
////    private Date relocationDate;
////
////    private TimeTableAvailableResponse timeTableAvailableResponse;
////
////    private Visit visit;
////
////    @Before
////    public void setUp() throws Exception {
////        relocationDate = DateUtils.parseDate("2011-11-11", new String[] {"yyyy-MM-dd"});
////
////        OrderType orderType = new OrderType();
////        orderType.setCode(ORDER_TYPE_CODE);
////
////        Order order = new Order();
////        order.setOrderType(orderType);
////
////        visit = new Visit();
////        visit.setId(VISIT_ID);
////        visit.setPostalCodeId(VISIT_POSTAL_CODE_ID);
////        visit.setVisitTypeCode(VISIT_TYPE_CODE);
////        visit.setOrder(order);
////
////        timeTableAvailableResponse = new TimeTableAvailableResponse();
////        timeTableAvailableResponse.setTimeSlotAvailable(true);
////        timeTableAvailableResponse.setTimetableTimeSlotId(TIME_TABLE_TIME_SLOT_ID);
////        timeTableAvailableResponse.setTimeSlotStart(TIME_TABLE_TIME_SLOT_START);
////        timeTableAvailableResponse.setTimeSlotEnd(TIME_TABLE_TIME_SLOT_END);
////
////        Address address = new Address();
////        address.setId(ADDRESS_ID);
////        address.setPostalCode(ADDRESS_POSTAL_CODE);
////        address.setPostalCodeId(ADDRESS_POSTAL_CODE_ID);
////        address.setCityId(ADDRESS_CITY_ID);
////        address.setCountryId(ADDRESS_COUNTRY_ID);
////
////        when(visitRepository.findByOrderCustomerIdAndId(CUSTOMER_ID, VISIT_ID)).thenReturn(Optional.of(visit));
//////        when(administrativeClient.timeTableAvailability(ORDER_TYPE_CODE, VISIT_POSTAL_CODE_ID, relocationDate, VISIT_TYPE_CODE, RELOCATION_TIME_SLOT_CODE)).thenReturn(timeTableAvailableResponse);
//////        when(customerClient.findAddressesByCustomerIdAndIdInAndActiveTrue(CUSTOMER_ID, ADDRESS_ID.toString())).thenReturn(new PagedResources<Address>(Stream.of(address).collect(toSet()), null));
////
////        when(visitRepository.save(visit)).thenAnswer(a -> {
////            return a.getArgumentAt(0, Visit.class);
////        });
////
////    }
////
////    @Test(expected = InvalidVisitException.class)
////    public void testErrorInvalidVisit() {
////        when(visitRepository.findByOrderCustomerIdAndId(CUSTOMER_ID, VISIT_ID)).thenReturn(Optional.empty());
////
////        visitServiceImpl.relocateVisit(CUSTOMER_ID, VISIT_ID, relocationDate, RELOCATION_TIME_SLOT_CODE);
////    }
////
////    @Test(expected = InvalidTimeSlotException.class)
////    public void testErrorNotTimeSlotAvailable() {
////        timeTableAvailableResponse.setTimeSlotAvailable(false);
////
////        visitServiceImpl.relocateVisit(CUSTOMER_ID, VISIT_ID, relocationDate, RELOCATION_TIME_SLOT_CODE);
////    }
////
////    @Test
////    public void testRelocateVisit() {
////        Visit relocatedVisit = visitServiceImpl.relocateVisit(CUSTOMER_ID, VISIT_ID, relocationDate, RELOCATION_TIME_SLOT_CODE);
////
////        verify(visitRepository).save(visit);
////
////        assertThat("Visit with id 1 should be relocated", relocatedVisit.getId(), is(visit.getId()));
////        assertThat("Visit should have date relocated", relocatedVisit.getDate(), is(relocationDate));
//////        assertThat("Visit should have time table time slot id set", relocatedVisit.getTimeTableTimeSlotId(), is(TIME_TABLE_TIME_SLOT_ID));
//////        assertThat("Visit start slot should be set", relocatedVisit.getTimeSlotStart(), is(TIME_TABLE_TIME_SLOT_START));
//// //       assertThat("Visit end slot should be set", relocatedVisit.getTimeSlotEnd(), is(TIME_TABLE_TIME_SLOT_END));
////    }
////
////    @Test(expected = InvalidAddressException.class)
////    public void testErrorInvalidAddress() {
////        when(customerClient.findAddressesByCustomerIdAndIdInAndActiveTrue(CUSTOMER_ID, ADDRESS_ID.toString())).thenReturn(null);
////
////        visitServiceImpl.relocateVisit(CUSTOMER_ID, VISIT_ID, relocationDate, RELOCATION_TIME_SLOT_CODE, ADDRESS_ID);
////    }
////
////    @Test
////    public void testRelocateVisitWithAddress() {
////        when(administrativeClient.timeTableAvailability(ORDER_TYPE_CODE, ADDRESS_POSTAL_CODE_ID, relocationDate, VISIT_TYPE_CODE, RELOCATION_TIME_SLOT_CODE)).thenReturn(timeTableAvailableResponse);
////
////        Visit relocatedVisit = visitServiceImpl.relocateVisit(CUSTOMER_ID, VISIT_ID, relocationDate, RELOCATION_TIME_SLOT_CODE, ADDRESS_ID);
////
////        verify(visitRepository).save(visit);
////
////        assertThat("Visit with id 1 should be relocated", relocatedVisit.getId(), is(visit.getId()));
////        assertThat("Visit should have date relocated", relocatedVisit.getDate(), is(relocationDate));
//////        assertThat("Visit should have time table time slot id set", relocatedVisit.getTimeTableTimeSlotId(), is(TIME_TABLE_TIME_SLOT_ID));
//////        assertThat("Visit start slot should be set", relocatedVisit.getTimeSlotStart(), is(TIME_TABLE_TIME_SLOT_START));
////  //      assertThat("Visit end slot should be set", relocatedVisit.getTimeSlotEnd(), is(TIME_TABLE_TIME_SLOT_END));
//////
////  //      assertThat("Visit address id should be updated", relocatedVisit.getAddressId(), is(ADDRESS_ID));
////        //assertThat("Visit postal code should be updated", relocatedVisit.getPostalCode(), is(ADDRESS_POSTAL_CODE));
////        //assertThat("Visit postal code id should be updated", relocatedVisit.getPostalCodeId(), is(ADDRESS_POSTAL_CODE_ID));
////        //assertThat("Visit city Id should be updated", relocatedVisit.getCityId(), is(ADDRESS_CITY_ID));
////        //assertThat("Visit country city Id should be updated", relocatedVisit.getCountryId(), is(ADDRESS_COUNTRY_ID));
////    }
////
//}
