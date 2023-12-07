package io.pivotal.springs3.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.springs3.config.S3ClientProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final S3ClientProperties s3ClientProperties;
    private final S3Client client;
    private final ObjectMapper mapper;

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
}
