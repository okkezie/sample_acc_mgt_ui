package com.management.account.controller;

import com.management.account.model.Transaction;
import com.management.account.request.CreateAccountRequest;
import com.management.account.request.DepositAccountRequest;
import com.management.account.request.WithdrawalAccountRequest;
import com.management.account.response.CreateAccountResponse;
import com.management.account.response.DepositAccountResponse;
import com.management.account.response.WithdrawalAccountResponse;
import com.management.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    private SecureRandom random = new SecureRandom();

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<CreateAccountResponse>> generateAccount() throws ExecutionException, InterruptedException {
        List<CreateAccountResponse> response = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            CreateAccountRequest request = new CreateAccountRequest();
            request.setAccountName("User " + i+1);
            request.setPhoneNumber("1234"+i);

            CreateAccountResponse result = accountService.createAccount(request).get();
            response.add(result);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deposit")
    public ResponseEntity<List<DepositAccountResponse>> randomDepositAccount(@RequestBody DepositAccountRequest request) throws ExecutionException, InterruptedException {
        List<DepositAccountResponse> response = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            var val = Math.round(random.nextDouble() * 10000.0) / 100.0;
            request.setAmountDeposited(BigDecimal.valueOf(val));
            DepositAccountResponse result = accountService.deposit(request).get();
            response.add(result);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<List<WithdrawalAccountResponse>> withdrawal(@RequestBody WithdrawalAccountRequest request) throws ExecutionException, InterruptedException {
        List<WithdrawalAccountResponse> response = new ArrayList<>();
        for(int i = 0; i < 25; i++) {
            var val = Math.round(random.nextDouble() * 10000.0) / 100.0;
            request.setAmountWithdrawn(BigDecimal.valueOf(val));
            WithdrawalAccountResponse result = accountService.withdrawal(request).get();
            response.add(result);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transaction/{accountNumber}")
    public ResponseEntity<List<Transaction>> withdrawal(@PathVariable(required = false) String accountNumber) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(accountService.transaction(accountNumber).get());
    }

}
