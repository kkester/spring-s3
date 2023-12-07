package io.pivotal.springs3.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@Data
@ConfigurationProperties(prefix = "s3")
public class S3ClientProperties {
    private String bucket;
    private URI endpoint;
    private String accessId;
    private String secretKey;
    private String region;
}
