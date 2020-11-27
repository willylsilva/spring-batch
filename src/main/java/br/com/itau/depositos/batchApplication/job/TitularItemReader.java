package br.com.itau.depositos.batchApplication.job;

import br.com.itau.depositos.batchApplication.datastore.TitularRepository;
import br.com.itau.depositos.batchApplication.dto.TitularDTO;
import br.com.itau.depositos.batchApplication.mapper.TitularMapper;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

public class TitularItemReader implements ItemReader<TitularDTO> {

    @Autowired
    private TitularRepository titularRepository;
    @Autowired
    private TitularMapper titularMapper;
    private Iterator<TitularDTO> titularIterator;

    @BeforeStep
    public void before(StepExecution stepExecution) {
        titularIterator = titularRepository.findAll().stream().map(titularMapper::from).iterator();
    }

    @Override
    public TitularDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (titularIterator != null && titularIterator.hasNext()) {
            return titularIterator.next();
        } else {
            return null;
        }
    }
}
