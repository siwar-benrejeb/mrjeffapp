package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.client.admin.model.ValidateAddressResponse;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.exception.InvalidAddressException;
import com.mrjeffapp.customer.repository.AddressRepository;
import com.mrjeffapp.customer.service.UpdateDefaultCustomerDeliveryAddressDto;
import com.mrjeffapp.customer.service.UpdateDefaultCustomerDeliveryAddressService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateDefaultCustomerDeliveryAddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AdministrativeClient administrativeClient;

    @InjectMocks
    private UpdateDefaultCustomerDeliveryAddressService updateDefaultCustomerDeliveryAddressService;

    private Address anAddress;
    private ValidateAddressResponse response;

    @Before
    public void setUp() {
        anAddress = new Address();
        anAddress.setPostalCode("12345");
        anAddress.setCityCode("VLC");
        anAddress.setCountryCode("ES");

        response = new ValidateAddressResponse();
    }

    @Test
    public void testItShouldFindAnAddress() {
        response.setValid(true);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));;
        when(administrativeClient.validatePostalCode(
                anAddress.getPostalCode(),
                anAddress.getCityCode(),
                anAddress.getCountryCode())).thenReturn(response);

        updateDefaultCustomerDeliveryAddressService.execute(new UpdateDefaultCustomerDeliveryAddressDto(
                "addressId",
                true,
                "customerId"
        ));

        verify(addressRepository, times(1)).findByCustomerIdAndIdAndActiveTrue("customerId", "addressId");
    }

    @Test
    public void testItShouldBeSetFalseDefaultDeliveryAddress() {
        anAddress.setDefaultDelivery(true);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        updateDefaultCustomerDeliveryAddressService.execute(new UpdateDefaultCustomerDeliveryAddressDto(
                "addressId",
                false,
                "customerId"

        ));

        assertEquals(false, anAddress.getDefaultDelivery());
    }


    @Test
    public void testItShouldBeSetTrueDefaultDeliveryAddress() {
        anAddress.setDefaultDelivery(true);
        response.setValid(true);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));;
        when(administrativeClient.validatePostalCode(
                anAddress.getPostalCode(),
                anAddress.getCityCode(),
                anAddress.getCountryCode())).thenReturn(response);

        updateDefaultCustomerDeliveryAddressService.execute(new UpdateDefaultCustomerDeliveryAddressDto(
                "addressId",
                true,
                "customerId"
        ));

        assertEquals(true, anAddress.getDefaultDelivery());
    }

    @Test(expected = InvalidAddressException.class)
    public void testItShouldBeThrowInvalidAddressException() {
        anAddress.setDefaultDelivery(false);
        response.setValid(false);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        when(administrativeClient.validatePostalCode(
                anAddress.getPostalCode(),
                anAddress.getCityCode(),
                anAddress.getCountryCode())).thenReturn(response);

        updateDefaultCustomerDeliveryAddressService.execute(new UpdateDefaultCustomerDeliveryAddressDto(
                "addressId",
                true,
                "customerId"

        ));

    }


    @Test
    public void testItShouldCallSave() {

        response.setValid(true);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));
        when(administrativeClient.validatePostalCode(
                anAddress.getPostalCode(),
                anAddress.getCityCode(),
                anAddress.getCountryCode())).thenReturn(response);


        updateDefaultCustomerDeliveryAddressService.execute(new UpdateDefaultCustomerDeliveryAddressDto(
                "addressId",
                true,
                "customerId"
        ));

        verify(addressRepository, times(1)).save(anAddress);
    }


    @Test
    public void testItShouldUpdateAllDefaultDeliveryAddress() {
        response.setValid(true);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));
        when(administrativeClient.validatePostalCode(
                anAddress.getPostalCode(),
                anAddress.getCityCode(),
                anAddress.getCountryCode())).thenReturn(response);

        updateDefaultCustomerDeliveryAddressService.execute(new UpdateDefaultCustomerDeliveryAddressDto(
                "addressId",
                true,
                "customerId"
        ));

        verify(addressRepository, times(1)).setDisableDefaultDeliveryAddresses("customerId");
    }


}
