package com.project.service;

import com.project.entities.Payment;
import com.project.entities.Ride;
import com.project.entities.enus.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus status);
}
