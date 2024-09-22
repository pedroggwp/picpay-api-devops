package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "conta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column(name = "numero_conta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @Column(name = "saldo")
    private double balance;

    @Column(name = "limite_especial")
    private double limit;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf")
    private Client client;
}