package com.derrickd.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class responsible for customer registration, fraud checks, and notifications.
 *
 * This service provides methods to register new customers, perform fraud checks,
 * and handle notifications based on the registration and fraud check results.
 *
 *     /**
 *      * Registers a new customer, performs fraud checks, and sends notifications.
 *      *
 *      * This method registers a new customer by saving their details in the database.
 *      * It then performs a fraud check on the customer using an external service
 *      * (assumed to be at http://localhost:8082/api/v1/fraud-check/{customerId}).
 *      * If the customer is flagged as a fraudster, an exception is thrown. Finally,
 *      * the method handles notifications based on the registration and fraud check results.
 *      *
 *      * @param request The CustomerRegistrationRequest containing customer details.
 *      * @throws IllegalStateException If the customer is flagged as a fraudster.
 *
 *    */
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;


    public void registerCustomer(CustomerRegistrationRequest request) {
        // Create a new customer based on the registration request.
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // Save the customer details in the database.
        customerRepository.saveAndFlush(customer);

        // Perform a fraud check using an external service.
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8082/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        // If the customer is flagged as a fraudster, throw an exception.
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        // Handle notifications based on the registration and fraud check results.
        // (Implementation of notification logic is not provided in this code snippet.)
    }
}
