package io.pivotal.springs3.config;

import io.pivotal.springs3.student.StudentEntity;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

    @Bean
    FlatFileItemReaderBuilder<StudentEntity> studentItemReaderBuilder() {
        return new FlatFileItemReaderBuilder<StudentEntity>()
            .name("studentItemReader")
            .delimited()
            .names("firstName", "lastName")
            .targetType(StudentEntity.class)
            .linesToSkip(1);
    }
}
