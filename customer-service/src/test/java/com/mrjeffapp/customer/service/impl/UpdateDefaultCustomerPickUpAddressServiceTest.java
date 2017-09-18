package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.client.admin.model.ValidateAddressResponse;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.exception.InvalidAddressException;
import com.mrjeffapp.customer.repository.AddressRepository;
import com.mrjeffapp.customer.service.UpdateDefaultCustomerPickUpAddressDto;
import com.mrjeffapp.customer.service.UpdateDefaultCustomerPickUpAddressService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateDefaultCustomerPickUpAddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AdministrativeClient  administrativeClient;

    @InjectMocks
    private UpdateDefaultCustomerPickUpAddressService updateDefaultCustomerPickUpAddressService;

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

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));
        when(administrativeClient.validatePostalCode(
                anAddress.getPostalCode(),
                anAddress.getCityCode(),
                anAddress.getCountryCode())).thenReturn(response);

        updateDefaultCustomerPickUpAddressService.execute(new UpdateDefaultCustomerPickUpAddressDto(
                "addressId",
                true,
                "customerId"

        ));

        verify(addressRepository, times(1)).findByCustomerIdAndIdAndActiveTrue("customerId", "addressId");
    }

    @Test
    public void testItShouldBeSetFalseDefaultPickUpAddress() {
        anAddress.setDefaultPickup(true);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        updateDefaultCustomerPickUpAddressService.execute(new UpdateDefaultCustomerPickUpAddressDto(
                "addressId",
                false,
                "customerId"
        ));

        assertEquals(false, anAddress.getDefaultPickup());
    }

    @Test
    public void testItShouldBeSetTrueDefaultPickUpAddress() {
        anAddress.setDefaultPickup(true);
        response.setValid(true);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        when(administrativeClient.validatePostalCode(
                anAddress.getPostalCode(),
                anAddress.getCityCode(),
                anAddress.getCountryCode())).thenReturn(response);

        updateDefaultCustomerPickUpAddressService.execute(new UpdateDefaultCustomerPickUpAddressDto(
                "addressId",
                true,
                "customerId"
        ));

        assertEquals(true, anAddress.getDefaultPickup());
    }

    @Test(expected = InvalidAddressException.class)
    public void testItShouldBeThrowInvalidAddressException() {
        anAddress.setDefaultPickup(false);
        response.setValid(false);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        when(administrativeClient.validatePostalCode(
                anAddress.getPostalCode(),
                anAddress.getCityCode(),
                anAddress.getCountryCode())).thenReturn(response);

        updateDefaultCustomerPickUpAddressService.execute(new UpdateDefaultCustomerPickUpAddressDto(
                "addressId",
                true,
                "customerId"
        ));

    }

    @Test
    public void testItShouldCallSave() {
        anAddress.setDefaultPickup(false);
        response.setValid(true);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        when(administrativeClient.validatePostalCode(
                anAddress.getPostalCode(),
                anAddress.getCityCode(),
                anAddress.getCountryCode())).thenReturn(response);

        updateDefaultCustomerPickUpAddressService.execute(new UpdateDefaultCustomerPickUpAddressDto(
                "addressId",
                true,
                "customerId"

        ));

        verify(addressRepository, times(1)).save(anAddress);
    }


    @Test
    public void testItShouldUpdateAllDefaultPickUpAddress() {
        anAddress.setDefaultPickup(true);
        response.setValid(true);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        when(administrativeClient.validatePostalCode(
                anAddress.getPostalCode(),
                anAddress.getCityCode(),
                anAddress.getCountryCode())).thenReturn(response);

        updateDefaultCustomerPickUpAddressService.execute(new UpdateDefaultCustomerPickUpAddressDto(
                "addressId",
                true,
                "customerId"
        ));

        verify(addressRepository, times(1)).setDisableDefaultPickupAddresses("customerId");
    }
}
