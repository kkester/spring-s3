package io.pivotal.springs3.student.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import io.pivotal.springs3.student.S3Adapter;
import io.pivotal.springs3.student.StudentResource;
import io.pivotal.springs3.student.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentCsvProcessor {

    private final StudentService studentService;
    private final StudentResourceRepository studentResourceRepository;
    private final S3Adapter s3Adapter;

    public void checkForStudentFiles() {
        List<String> csvFileNames = s3Adapter.getCsvFileNames();
        csvFileNames.forEach(key -> {
            StudentResource studentResource = s3Adapter.getStudentResource(key);
            boolean saved = studentResourceRepository.save(studentResource);
            if (saved) {
                this.readStudents(studentResource.getInputStream());
            }
        });
    }

    private void readStudents(InputStream studentInputStream) {

        List<StudentCsv> studentEntities = new CsvToBeanBuilder(new InputStreamReader(studentInputStream))
            .withType(StudentCsv.class)
            .build()
            .parse();
        studentEntities.forEach(studentCsv -> {
            log.info("Saving student {}", studentCsv);
            studentService.persist(studentCsv);
        });
    }
}
