package io.pivotal.springs3.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.springs3.config.S3ClientProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;
    private final ProcessedFileRepository processedFileRepository;
    private final S3ClientProperties s3ClientProperties;
    private final S3Client client;
    private final ObjectMapper mapper;
    private final FlatFileItemReaderBuilder<StudentEntity> studentItemReaderBuilder;

    public Student save(Student student) {
        StudentEntity studentEntity = StudentEntity.builder()
            .id(UUID.randomUUID())
            .firstName(student.getFirstName())
            .lastName(student.getLastName())
            .build();
        StudentEntity savedStudentEntity = studentRepository.save(studentEntity);
        Student savedStudent = Student.builder()
            .id(savedStudentEntity.getId())
            .firstName(savedStudentEntity.getFirstName())
            .lastName(savedStudentEntity.getLastName())
            .build();
        storeInS3(savedStudent);
        return savedStudent;
    }

    public List<Student> findAll() {
        return studentRepository.findAll().stream()
            .map(s -> Student.builder()
                .id(s.getId())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .build())
            .toList();
    }

    @SneakyThrows
    public void storeInS3(Student student) {
        client.putObject(
            PutObjectRequest.builder()
                .bucket(s3ClientProperties.getBucket())
                .key(student.getId().toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .acl(ObjectCannedACL.AUTHENTICATED_READ)
                .build(),
            RequestBody.fromString(mapper.writeValueAsString(student)));
    }

    public void checkForStudentFiles() {
        ListObjectsV2Response listObjectsResponse = client.listObjectsV2(
            ListObjectsV2Request.builder()
                .bucket(s3ClientProperties.getBucket())
                .build());

        Stream<String> s3ObjectKeys = listObjectsResponse.contents().stream()
            .map(S3Object::key)
            .filter(key -> key.endsWith(".csv"));

        s3ObjectKeys.forEach(key -> {
            ResponseInputStream<GetObjectResponse> getObjectResponseStream = client.getObject(
                GetObjectRequest.builder()
                    .bucket(s3ClientProperties.getBucket())
                    .key(key)
                    .build());
            boolean saved = processedFileRepository.save(key, getObjectResponseStream.response());
            if (saved) {
                this.readStudents(getObjectResponseStream);
            }
        });
    }

    @SneakyThrows
    private void readStudents(ResponseInputStream<GetObjectResponse> getObjectResponseStream) {
        Resource resource = new InputStreamResource(getObjectResponseStream);
        FlatFileItemReader<StudentEntity> studentItemReader = studentItemReaderBuilder.resource(resource).build();
        studentItemReader.open(new ExecutionContext());
        StudentEntity student = studentItemReader.read();
        while (student != null) {
            log.info("Saving student {}", student);
            student.setId(UUID.randomUUID());
            studentRepository.save(student);
            student = studentItemReader.read();
        }
    }
}
