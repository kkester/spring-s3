package io.pivotal.springs3.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.springs3.config.S3ClientProperties;
import io.pivotal.springs3.student.api.Student;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class S3Adapter {
    private final S3Client client;
    private final S3ClientProperties s3ClientProperties;
    private final ObjectMapper mapper;

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

    public List<String> getCsvFileNames() {
        ListObjectsV2Response listObjectsResponse = client.listObjectsV2(
            ListObjectsV2Request.builder()
                .bucket(s3ClientProperties.getBucket())
                .build());

        return listObjectsResponse.contents().stream()
            .map(S3Object::key)
            .filter(key -> key.endsWith(".csv"))
            .toList();
    }

    public StudentResource getStudentResource(String key) {
        ResponseInputStream<GetObjectResponse> getObjectResponseStream = client.getObject(
            GetObjectRequest.builder()
                .bucket(s3ClientProperties.getBucket())
                .key(key)
                .build());
        return StudentResource.builder()
            .name(key)
            .inputStream(getObjectResponseStream)
            .lastModified(getObjectResponseStream.response().lastModified())
            .build();
    }
}
