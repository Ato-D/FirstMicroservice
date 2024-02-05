package com.derrickd.fraud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service class responsible for fraud checks and recording fraud check history.
 *
 * This service provides methods to perform fraud checks on customers and record
 * the results in the fraud check history repository.
 */
@Service
@AllArgsConstructor
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    /**
     * Checks whether the customer is fraudulent and records the result in history.
     *
     * This method performs a fraud check on the specified customer, records the result
     * (whether the customer is a fraudster or not) in the fraud check history repository,
     * and returns the result.
     *
     * @param customerId The unique identifier of the customer to be checked.
     * @return true if the customer is marked as a fraudster, false otherwise.
     */
    public boolean isFraudulentCustomer(Integer customerId) {
        // Save the result of the fraud check in the history repository.
        fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(false)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        // For simplicity, always return false. In a real implementation, this would
        // be determined based on the actual fraud check logic.
        return false;
    }
}
