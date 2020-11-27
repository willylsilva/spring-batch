package br.com.itau.depositos.batchApplication.job;

import br.com.itau.depositos.batchApplication.dto.StatusRegistro;
import br.com.itau.depositos.batchApplication.dto.TitularDTO;
import org.springframework.batch.item.ItemProcessor;

public class TitularItemProcessor implements ItemProcessor<TitularDTO, TitularDTO> {

    @Override
    public TitularDTO process(TitularDTO o) throws Exception {
        return new TitularDTO(
                o.getId(),
                o.getNome(),
                o.getCpf(),
                o.getIdade(),
                o.getIdade() > 18 ? StatusRegistro.CONSISTENTE : StatusRegistro.INCONSISTENTE
        );
    }
}
