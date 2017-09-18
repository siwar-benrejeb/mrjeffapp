package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.repository.AddressRepository;
import com.mrjeffapp.customer.service.UpdateDefaultCustomerBillingAddressDto;
import com.mrjeffapp.customer.service.UpdateDefaultCustomerBillingAddressService;
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
public class UpdateDefaultCustomerBillingAddressServiceTest {
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AdministrativeClient administrativeClient;

    @InjectMocks
    private UpdateDefaultCustomerBillingAddressService updateDefaultCustomerBillingAddressService;

    private Address anAddress;

    @Before
    public void setUp()
    {
        anAddress = new Address();
        anAddress.setPostalCode("12345");
        anAddress.setCityCode("VLC");
        anAddress.setCountryCode("ES");
    }

    @Test
    public void testItShouldFindAnAddress() {
        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        updateDefaultCustomerBillingAddressService.execute(new UpdateDefaultCustomerBillingAddressDto(
                "addressId",
                true,
                "customerId"
        ));

        verify(addressRepository, times(1)).findByCustomerIdAndIdAndActiveTrue("customerId", "addressId");
    }

    @Test
    public void testItShouldBeSetTrueDefaultBillingAddress() {
        anAddress.setDefaultBilling(true);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        updateDefaultCustomerBillingAddressService.execute(new UpdateDefaultCustomerBillingAddressDto(
                "addressId",
                true,
                "customerId"

        ));

        assertEquals(true, anAddress.getDefaultBilling());
    }

    @Test
    public void testItShouldBeSetFalseDefaultBillingAddress() {
        anAddress.setDefaultBilling(false);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        updateDefaultCustomerBillingAddressService.execute(new UpdateDefaultCustomerBillingAddressDto(
                "addressId",
                true,
                "customerId"
        ));

        assertEquals(true, anAddress.getDefaultBilling());
    }

    @Test
    public void testItShouldCallSave() {
        anAddress.setDefaultBilling(false);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        updateDefaultCustomerBillingAddressService.execute(new UpdateDefaultCustomerBillingAddressDto(
                "addressId",
                true,
                "customerId"
        ));

        verify(addressRepository, times(1)).save(anAddress);
    }

    @Test
    public void testItShouldUpdateAllDefaultBillingAddress() {
        anAddress.setDefaultBilling(false);

        when(addressRepository.findByCustomerIdAndIdAndActiveTrue("customerId", "addressId")).thenReturn(Optional.of(anAddress));

        updateDefaultCustomerBillingAddressService.execute(new UpdateDefaultCustomerBillingAddressDto(
                "addressId",
                true,
                "customerId"
        ));

        verify(addressRepository, times(1)).setDisableDefaultBillingAddresses("customerId");
    }
}
