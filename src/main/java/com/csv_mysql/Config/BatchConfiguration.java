package com.csv_mysql.Config;


import com.csv_mysql.Model.Order;
import com.csv_mysql.Process.JobCompletionNotificationListener;
import com.csv_mysql.Process.OrderItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public FlatFileItemReader<Order> orderItemReader() {
        return new FlatFileItemReaderBuilder<Order>()
                .name("orderItemReader")
                .resource(new ClassPathResource("test_file_2.csv"))// Set the path to your CSV file
                .delimited()
                .names(new String[]{"phoneNumber"})// Specify the column names in the CSV file
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Order>() {{
                    setTargetType(Order.class);
                }})
                .build();
    }


    @Bean
    public OrderItemProcessor orderItemProcessor() {
        return new OrderItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Order> orderItemWriter() {
        return new JdbcBatchItemWriterBuilder<Order>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO orders (phoneNumber, country) VALUES (:phoneNumber, :country)")// SQL query to insert data into the database
                .dataSource(this.dataSource)// Inject the data source for database connectivity
                .build();
    }

    @Bean
    public Step processOrderStep(ItemReader<Order> reader, ItemProcessor<Order, Order> processor,
                                 ItemWriter<Order> writer) {
        return stepBuilderFactory.get("processOrderStep")
                .<Order, Order>chunk(100)// Process items in chunks of 100
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job processOrderJob(JobCompletionNotificationListener listener, Step processOrderStep) {
        return jobBuilderFactory.get("processOrderJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)// Add a listener to receive job completion notifications
                .flow(processOrderStep)
                .end()
                .build();
    }
}
