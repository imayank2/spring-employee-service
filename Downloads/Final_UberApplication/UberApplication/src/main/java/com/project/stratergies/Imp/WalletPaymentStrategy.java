package com.project.stratergies.Imp;
//Rider had 232, Driver had 500
//Ride cost is 100, commission = 30
//Rider -> 232-100 = 132
//Driver -> 500 + (100 - 30) = 570

import com.project.entities.Driver;
import com.project.entities.Payment;
import com.project.entities.Rider;
import com.project.entities.enus.PaymentStatus;
import com.project.entities.enus.TransactionMethod;
import com.project.repository.PaymentRepository;
import com.project.service.WalletService;
import com.project.stratergies.PaymentStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(),
                payment.getAmount(), null, payment.getRide(), TransactionMethod.RIDE);

        double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(),
                driversCut, null, payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
