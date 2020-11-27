package br.com.itau.depositos.batchApplication.datastore;

import br.com.itau.depositos.batchApplication.dto.TitularDTO;
import br.com.itau.depositos.batchApplication.mapper.TitularMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TitularDataStore {

    private final TitularRepository titularRepository;
    private final TitularMapper titularMapper;

    public List<TitularDTO> getAllIdade(int idade) {
        return titularRepository.findAllByIdade(idade).stream().map(titularMapper::from).collect(Collectors.toList());
    }

}
