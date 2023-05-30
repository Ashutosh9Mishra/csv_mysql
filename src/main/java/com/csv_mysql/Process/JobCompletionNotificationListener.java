package com.csv_mysql.Process;


import com.csv_mysql.Config.BatchConfiguration;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private final BatchConfiguration orderRepository;

    public JobCompletionNotificationListener(BatchConfiguration orderRepository) {
        this.orderRepository =  orderRepository;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
           System.out.println("Job is successfull! ");
        }
    }
}

