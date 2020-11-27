package br.com.itau.depositos.batchApplication.mapper;

import br.com.itau.depositos.batchApplication.datastore.TitularEntity;
import br.com.itau.depositos.batchApplication.dto.TitularDTO;
import org.springframework.stereotype.Component;

@Component
public class TitularMapper {
    public TitularDTO from(TitularEntity titularEntities) {
        return new TitularDTO(
                titularEntities.getId(),
                titularEntities.getNome(),
                titularEntities.getCpf(),
                titularEntities.getIdade(),
                null
        );
    }
}
