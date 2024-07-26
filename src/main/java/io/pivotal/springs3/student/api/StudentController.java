package io.pivotal.springs3.student.api;

import io.pivotal.springs3.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.storeStudent(student);
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.findAll();
    }
}
