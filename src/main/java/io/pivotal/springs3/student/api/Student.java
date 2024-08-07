package io.pivotal.springs3.student.api;

import io.pivotal.springs3.student.IStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements IStudent {
    private UUID id;
    private String firstName;
    private String lastName;
}
