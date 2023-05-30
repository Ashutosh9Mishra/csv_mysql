package com.csv_mysql.Service;


import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final JobLauncher jobLauncher;
    private final Job processOrderJob;

    public OrderServiceImpl(JobLauncher jobLauncher, Job processOrderJob) {
        this.jobLauncher = jobLauncher;
        this.processOrderJob = processOrderJob;
    }

    @Override
    public void processOrders() {
        try {
            JobExecution jobExecution = jobLauncher.run(processOrderJob, new JobParameters());
            //  handling after job execution
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                System.out.println("Job is succes");
            } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
                // Job failed
                System.out.println("Job is failed");
            }
        } catch (JobExecutionException e) {

        }
    }

}

