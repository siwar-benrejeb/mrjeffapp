package com.mrjeffapp.customer.service;

import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.domain.CustomerNote;
import com.mrjeffapp.customer.domain.CustomerType;
import com.mrjeffapp.customer.exception.CustomerNotFoundException;
import com.mrjeffapp.customer.exception.InvalidAddressException;
import com.mrjeffapp.customer.exception.InvalidCustomerException;
import com.mrjeffapp.customer.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppendCustomerNoteServiceTest {


    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AppendCustomerNoteService appendCustomerNoteService;

    private Customer customer;

    @Before
    public void setUp()
    {
        customer = new Customer("a@a.com",
                "1234",
                "name",
                "lastName",
                "123456789",
                new CustomerType("code",
                        "name",
                        "description",
                        true));
    }

    @Test
    public void itShouldBeTrue(){
        assertTrue(true);
    }

    @Test
    public void itShouldBeFindaCustomer(){

        when(customerRepository.findById("customerId")).thenReturn(Optional.of(customer));

        appendCustomerNoteService.execute(new AppendCustomerNoteDto(
                "customerId",
                "note",
                "userId"
        ));

        assertEquals("name", customer.getName());

    }

    @Test(expected = CustomerNotFoundException.class)
    public void testItShouldBeThrowCustomerNotFoundException() {
        when(customerRepository.findById("customerId")).thenReturn(Optional.empty());

        appendCustomerNoteService.execute(new AppendCustomerNoteDto(
                "customerId",
                "note",
                "userId"
        ));

    }


    @Test
    public void itShouldHaveACustomerNote(){

        when(customerRepository.findById("customerId")).thenReturn(Optional.of(customer));

        appendCustomerNoteService.execute(new AppendCustomerNoteDto(
                "customerId",
                "note",
                "userId"
        ));

        Optional<CustomerNote> optionalCustomerNote = customer.getCustomerNote().stream().findFirst();
        CustomerNote customerNote = optionalCustomerNote.get();

        assertEquals("note", customerNote.getNote());
    }
}