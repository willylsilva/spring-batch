package br.com.itau.depositos.batchApplication.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class TitularDTO {
    UUID id;
    String nome;
    String cpf;
    int idade;
    StatusRegistro statusRegistro;
}
