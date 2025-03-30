package com.project.service;

import com.project.entities.Ride;
import com.project.entities.User;
import com.project.entities.Wallet;
import com.project.entities.enus.TransactionMethod;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount,
                            String transactionId, Ride ride,
                            TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, Double amount,
                                 String transactionId, Ride ride,
                                 TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);

}
