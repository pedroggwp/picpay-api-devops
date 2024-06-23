package com.example.service;

import com.example.entity.Client;
import com.example.exception.ClientAlreadyExistsException;
import com.example.exception.CustomProblemDetail;
import com.example.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clienteRepository;

    public ClientService(ClientRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Client> getAllClients() {
        return clienteRepository.findAll();
    }

    public Optional<Client> getOneClient(String cpf) {
        return clienteRepository.findById(cpf);
    }

    public void createClient(Client client) {
        String conflictField = getConflictField(client);

        if (conflictField != null) {
            CustomProblemDetail problemDetail = new CustomProblemDetail(HttpStatus.CONFLICT, "Client Already Exists", "Client with " + conflictField + " already exists.");
            throw new ClientAlreadyExistsException(problemDetail);
        }

        clienteRepository.save(client);
    }

    private String getConflictField(Client client) {
        if (clienteRepository.findByCpf(client.getCpf()).isPresent()) {
            return "CPF: " + client.getCpf();
        }

        if (clienteRepository.findByEmail(client.getEmail()).isPresent()) {
            return "email: " + client.getEmail();
        }

        if (clienteRepository.findByTelephone(client.getTelephone()).isPresent()) {
            return "telephone: " + client.getTelephone();
        }

        return null;
    }

    public Optional<Client> deleteClient(String cpf) {
        Optional<Client> optionalClient = clienteRepository.findById(cpf);

        if (optionalClient.isPresent()) {
            clienteRepository.deleteById(optionalClient.get().getCpf());
            return optionalClient;
        }

        return Optional.empty();
    }

    public Client updateClient(String cpf, Client updatedClient) {
        Optional<Client> optionalClient = clienteRepository.findById(cpf);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();

            client.setName(updatedClient.getName());
            client.setEmail(updatedClient.getEmail());
            client.setTelephone(updatedClient.getTelephone());

            clienteRepository.save(client);

            return client;
        }

        return null;
    }
}