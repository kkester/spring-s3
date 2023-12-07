package io.pivotal.springs3.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentEntity {
    @Id
    private UUID id;

    private String firstName;

    private String lastName;
}
