package io.pivotal.springs3.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3ClientConfig {
    @Bean
    @SneakyThrows
    public S3Client s3Client(S3ClientProperties config) {
        return S3Client.builder()
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        config.getAccessId(),
                        config.getSecretKey())))
            .region(Region.of(config.getRegion()))
            .endpointOverride(config.getEndpoint())
            .build();
    }

    @Bean
    @SneakyThrows
    public S3Presigner s3Presigner(S3ClientProperties config) {
        return S3Presigner.builder()
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        config.getAccessId(),
                        config.getSecretKey())))
            .region(Region.of(config.getRegion()))
            .endpointOverride(config.getEndpoint())
            .build();
    }
}
