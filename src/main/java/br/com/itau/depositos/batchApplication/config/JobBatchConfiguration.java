package br.com.itau.depositos.batchApplication.config;

import br.com.itau.depositos.batchApplication.dto.TitularDTO;
import br.com.itau.depositos.batchApplication.job.TitularItemProcessor;
import br.com.itau.depositos.batchApplication.job.TitularItemReader;
import br.com.itau.depositos.batchApplication.job.TitularItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public TitularItemReader reader() {
        return new TitularItemReader();
    }

    @Bean
    public TitularItemProcessor processor() {
        return new TitularItemProcessor();
    }

    @Bean
    public TitularItemWriter writer() {
        return new TitularItemWriter();
    }

    @Bean
    public Job job(Step step) {
        Job job = jobBuilderFactory.get("job1")
                .flow(step)
                .end()
                .build();
        return job;
    }

    @Bean
    public Step step(TitularItemReader reader,
                     TitularItemWriter writer,
                     TitularItemProcessor processor) {

        TaskletStep step = stepBuilderFactory.get("step1")
                .<TitularDTO, TitularDTO>chunk(100)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
        return step;
    }

}
