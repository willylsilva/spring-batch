package br.com.itau.depositos.batchApplication.job;

import br.com.itau.depositos.batchApplication.dto.TitularDTO;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class TitularItemWriter implements ItemWriter<TitularDTO> {
    @Override
    public void write(List<? extends TitularDTO> list) throws Exception {
        list.forEach(System.out::println);
    }
}
