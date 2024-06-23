package com.example.repository;

import com.example.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {

    Optional<Client> findByCpf(String cpf);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByTelephone(String telephone);
}
