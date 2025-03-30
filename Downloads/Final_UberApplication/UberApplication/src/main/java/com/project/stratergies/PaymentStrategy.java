package com.project.stratergies;

import com.project.entities.Payment;

public interface PaymentStrategy {
    Double PLATFORM_COMMISSION = 0.3;
    void processPayment(Payment payment);

}
