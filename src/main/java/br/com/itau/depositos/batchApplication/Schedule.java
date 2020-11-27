package br.com.itau.depositos.batchApplication;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Schedule {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Scheduled(cron = "${cron.time}")
    public void schedule() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
//        jobLauncher.run(job, new JobParameters());
        AtomicInteger batchRunCounter = new AtomicInteger(0);

        Date date = new Date();
        JobExecution jobExecution = jobLauncher
                .run(job, new JobParametersBuilder()
                        .addDate("launchDate", date)
                        .toJobParameters());
        batchRunCounter.incrementAndGet();
    }
}
