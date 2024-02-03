package com.derrickd.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
