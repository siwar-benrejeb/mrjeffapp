package com.mrjeffapp.customer.service;

import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.domain.CustomerNote;
import com.mrjeffapp.customer.exception.CustomerNotFoundException;
import com.mrjeffapp.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AppendCustomerNoteService implements ApplicationService {
    private static final Logger LOG = LoggerFactory.getLogger(AppendCustomerNoteDto.class);

    private CustomerRepository customerRepository;

    @Autowired
    public AppendCustomerNoteService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer execute(AppendCustomerNoteDto appendCustomerNoteDto) throws CustomerNotFoundException {

        Optional<Customer> optionalCustomer = this.customerRepository.findById(appendCustomerNoteDto.getCustomerId());

        if(!optionalCustomer.isPresent()) {
            throw new CustomerNotFoundException("Customer id not exists");
        }

        Customer customer = optionalCustomer.get();

        CustomerNote note = new CustomerNote(
                customer,
                appendCustomerNoteDto.getNote(),
                appendCustomerNoteDto.getUserId());

        customer.appendNote(note);

        customerRepository.save(customer);

        return customer;
    }
}
