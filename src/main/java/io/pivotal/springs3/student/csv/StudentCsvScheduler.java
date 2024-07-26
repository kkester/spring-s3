package io.pivotal.springs3.student.csv;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentCsvScheduler {

    private final StudentCsvProcessor studentCsvProcessor;

    @Scheduled(fixedDelay = 30000)
    @SneakyThrows
    public void perform() {

        log.info("Scheduler Started");

        studentCsvProcessor.checkForStudentFiles();

        log.info("Scheduler finished.");
    }
}
