package com.tp.batchtp.config;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class Launcher {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Scheduled(cron = "0 0 0 1 * *")
    public JobExecution launchJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("date", new JobParameter(Date.from(Instant.now())));
        JobParameters jobParameters = new JobParameters(parameters);
        return jobLauncher.run(job, jobParameters);
    }
}