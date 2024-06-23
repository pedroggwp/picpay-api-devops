package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @CPF(message = "CPF not valid")
    @NotNull(message = "CPF should not be null")
    private String cpf;

    @Column(name = "nome")
    @NotNull(message = "Name should not be null")
    private String name;

    @Email(message = "Email not valid")
    @NotNull(message = "Email should not be null")
    private String email;

//    @Size(min = 10, max = 15, message = "Telephone should have min 10 characters and max 15 characters")
    @Column(name = "telefone")
    @NotNull(message = "Telephone should not be null")
    @Pattern(regexp = "\\d{10,15}", message = "Telephone should have min 10 characters and max 15 characters")
    private String telephone;

//    @OneToMany
//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Account> accounts;
}