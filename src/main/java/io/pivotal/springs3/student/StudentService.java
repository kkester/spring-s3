package io.pivotal.springs3.student;

import io.pivotal.springs3.student.api.Student;
import io.pivotal.springs3.student.csv.StudentCsv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;
    private final S3Adapter s3Adapter;

    public Student saveStudent(Student student) {
        StudentEntity savedStudentEntity = save(student);
        Student savedStudent = Student.builder()
            .id(savedStudentEntity.getId())
            .firstName(savedStudentEntity.getFirstName())
            .lastName(savedStudentEntity.getLastName())
            .build();
        s3Adapter.storeInS3(savedStudent);
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

    public StudentEntity save(IStudent student) {
        StudentEntity studentEntity = StudentEntity.builder()
            .id(UUID.randomUUID())
            .firstName(student.getFirstName())
            .lastName(student.getLastName())
            .build();
        return studentRepository.save(studentEntity);
    }
}
