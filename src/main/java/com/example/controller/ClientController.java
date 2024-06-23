package com.example.controller;

import com.example.entity.Client;
import com.example.exception.CustomProblemDetail;
import com.example.service.ClientService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    private final ClientService clientService;
    private final org.springframework.validation.Validator validator;

    public ClientController(ClientService clientService, org.springframework.validation.Validator validator) {
        this.clientService = clientService;
        this.validator = validator;
    }

    @PostMapping("/client")
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorsMap(bindingResult.getFieldErrors()));
        }

        clientService.createClient(client);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/client")
    @ResponseBody
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/client/{cpf}")
    public ResponseEntity<?> getOneClient(@PathVariable String cpf) {
        Optional<Client> optionalClient = clientService.getOneClient(cpf);

        if (optionalClient.isPresent()) {
            return ResponseEntity.ok(optionalClient.get());
        }

        CustomProblemDetail errorResponse = new CustomProblemDetail(
                HttpStatus.NOT_FOUND,
                "Cliente não cadastrado",
                "Cliente com CPF: " + cpf + " não encontrado!"
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @Transactional
    @PutMapping("/client/{cpf}")
    public ResponseEntity<?> updateClient(@PathVariable String cpf,
                                           @Valid @RequestBody Client clientUpdated,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorsMap(bindingResult.getFieldErrors()));
        }

        Client client = clientService.updateClient(cpf, clientUpdated);

        if (client != null) {
            return ResponseEntity.status(HttpStatus.OK).body(client);
        }

        CustomProblemDetail errorResponse = new CustomProblemDetail(
                HttpStatus.NOT_FOUND,
                "Cliente não cadastrado",
                "Cliente com CPF: " + cpf + " não encontrado!"
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @PatchMapping("/client/{cpf}")
    public ResponseEntity<?> updatePartialClient(@PathVariable String cpf,
                                                 @RequestBody Map<String,
                                                         Object> updates) {
        Optional<Client> client = clientService.getOneClient(cpf);

        if (client.isPresent()) {
            Client client1 = client.get();
            System.out.println(client1);

            if (updates.containsKey("name")) {
                client1.setName((String) updates.get("name"));
            }

            if (updates.containsKey("email")) {
                client1.setEmail((String) updates.get("email"));
            }

            if (updates.containsKey("telephone")) {
                client1.setTelephone((String) updates.get("telephone"));
            }

            DataBinder binder = new DataBinder(client1);
            binder.setValidator(validator);
            binder.validate();

            BindingResult result = binder.getBindingResult();


            if (result.hasErrors()) {
                Map<String, String> errors = getErrorsMap(result.getFieldErrors());
                return ResponseEntity.badRequest().body(errors);
            }

            return ResponseEntity.ok().build();
        }

        CustomProblemDetail errorResponse = new CustomProblemDetail(
                HttpStatus.NO_CONTENT,
                "Cliente não cadastrado",
                "Cliente com CPF: " + cpf + " não encontrado!"
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @DeleteMapping("/client/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteClient(@PathVariable String cpf) {
        Optional<Client> optionalClient = clientService.deleteClient(cpf);

        if (optionalClient.isPresent()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private Map<String, String> getErrorsMap(List<FieldError> fieldErrors) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : fieldErrors) {
            String field = error.getField();
            String message = error.getDefaultMessage();

            errorMap.put(field, message);
        }
        return !errorMap.isEmpty() ? errorMap : null;
    }
}