package br.com.itau.depositos.batchApplication.datastore;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TitularRepository extends JpaRepository<TitularEntity, UUID> {
    List<TitularEntity> findAllByIdade(int idade);
}
