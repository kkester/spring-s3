package io.pivotal.springs3.student.csv;

import io.pivotal.springs3.student.StudentResource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class StudentResourceRepository {

    private final Map<String, Instant> fileKeyMap = new HashMap<>();

    public boolean save(StudentResource studentResource) {
        Instant lastModified = studentResource.getLastModified();
        String name = studentResource.getName();
        Instant instant = fileKeyMap.get(name);
        if (instant == null || instant.isBefore(lastModified)) {
            fileKeyMap.put(name, lastModified);
            return true;
        }
        return false;
    }
}
