package br.com.itau.depositos.batchApplication.datastore;

import lombok.Data;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@Value
public class TitularEntity {
    @Id
    UUID id;
    String nome;
    String cpf;
    int idade;

}
