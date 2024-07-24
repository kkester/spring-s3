package io.pivotal.springs3.student;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessedFileRepository {
    private final Map<String, Instant> fileKeyMap = new HashMap<>();

    public boolean save(String key, GetObjectResponse response) {
        Instant lastModified = response.lastModified();
        Instant instant = fileKeyMap.get(key);
        if (instant == null || instant.isBefore(lastModified)) {
            fileKeyMap.put(key, lastModified);
            return true;
        }
        return false;
    }
}
