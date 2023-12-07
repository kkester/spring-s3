package io.pivotal.springs3.config;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

@Component
public class S3HealthIndicator extends AbstractHealthIndicator {
    private final S3Client client;

    public S3HealthIndicator(S3Client s3Client) {
        super("MongoDB health check failed");
        Assert.notNull(s3Client, "MongoTemplate must not be null");
        this.client = s3Client;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        ListBucketsResponse listBucketsResponse = client.listBuckets();
        builder.up().withDetail("bucketSize", listBucketsResponse.buckets().size());
    }
}
