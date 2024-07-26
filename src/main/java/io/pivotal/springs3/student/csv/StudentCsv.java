package io.pivotal.springs3.student.csv;

import com.opencsv.bean.CsvBindByName;
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
public class StudentCsv implements IStudent {
    @CsvBindByName(column = "first_name")
    private String firstName;
    @CsvBindByName(column = "last_name")
    private String lastName;
}
