package com.csv_mysql;

import com.csv_mysql.Service.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job processOrderJob;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testProcessOrders() throws JobExecutionException {
        JobExecution mockJobExecution = new JobExecution(1L);
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(mockJobExecution);

        orderService.processOrders();

        // Add assertions to verify the behavior or outcome of the method call
        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
    }
}
