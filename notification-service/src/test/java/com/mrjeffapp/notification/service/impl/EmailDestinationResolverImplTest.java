package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.client.model.Customer;
import com.mrjeffapp.notification.service.EmailDestinationResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailDestinationResolverImplTest {
    private static final String DEFAULT_EMAIL = "DEFAULT_EMAIL@mrjeffapp.com";
    private static final String CUSTOMER_EMAIL = "CUSTOMER_EMAIL@mrjeffapp.com";

    private static final String PROFILE_TEST_A = "TEST_A";
    private static final String PROFILE_DEV = "dev";
    private static final String PROFILE_PRE = "pre";
    private static final String PROFILE_PRO = "pro";

    @Mock
    private Environment environment;

    private EmailDestinationResolverImpl emailDestinationResolver;

    private Customer customer;

    private String[] profiles;

    @Before
    public void setUp() {
        customer = new Customer();
        customer.setEmail(CUSTOMER_EMAIL);

    }

    @Test
    public void testMockUpAddressWhenProfileDEV() {
        EmailDestinationResolver emailDestinationResolver = new EmailDestinationResolverImpl(environment, DEFAULT_EMAIL);

        profiles = new String[]{PROFILE_TEST_A, PROFILE_DEV};
        when(environment.getActiveProfiles()).thenReturn(profiles);

        String email = emailDestinationResolver.getDestinationEmail(customer);
        assertThat("Destination email should be default email", email, is(DEFAULT_EMAIL));
    }

    @Test
    public void testCustomerAddressWhenProfilePRE() {
        EmailDestinationResolver emailDestinationResolver = new EmailDestinationResolverImpl(environment, DEFAULT_EMAIL);

        profiles = new String[]{PROFILE_TEST_A, PROFILE_PRE};
        when(environment.getActiveProfiles()).thenReturn(profiles);

        String email = emailDestinationResolver.getDestinationEmail(customer);
        assertThat("Destination email should be default email", email, is(DEFAULT_EMAIL));
    }

    @Test
    public void testCustomerAddressWhenProfilePRO() {
        EmailDestinationResolver emailDestinationResolver = new EmailDestinationResolverImpl(environment, DEFAULT_EMAIL);

        profiles = new String[]{PROFILE_TEST_A, PROFILE_PRO};
        when(environment.getActiveProfiles()).thenReturn(profiles);

        String email = emailDestinationResolver.getDestinationEmail(customer);
        assertThat("Destination email should be default email", email, is(CUSTOMER_EMAIL));
    }

}
