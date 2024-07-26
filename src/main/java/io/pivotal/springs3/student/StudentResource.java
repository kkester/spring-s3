package io.pivotal.springs3.student;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.time.Instant;

@Getter
@Builder
public class StudentResource {
    private String name;
    private InputStream inputStream;
    private Instant lastModified;
}
