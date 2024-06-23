package com.example.controller;

import com.example.entity.Account;
//import com.example.exception.CustomProblemDetail;
import com.example.exception.CustomProblemDetail;
import com.example.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account")
    @ResponseBody
    public ResponseEntity<?> getAllAccounts() {
        List<Account> accountList = accountService.getAllAccounts();

        if (accountList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma conta encontrada!");
        }

        return ResponseEntity.ok(accountList);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<?> getOneAccount(@PathVariable String id) {
        Optional<Account> optionalAccount = accountService.getOneAccount(id);

        if (optionalAccount.isPresent()) {
            return ResponseEntity.ok(optionalAccount.get());
        }

        CustomProblemDetail errorResponse = new CustomProblemDetail(
                HttpStatus.NO_CONTENT,
                "Conta não cadastrada",
                "Conta com ID: " + id + " não encontrada!"
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}