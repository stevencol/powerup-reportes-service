package co.com.reportes.sqs.sender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.adapter.sqs.sender.report")
public record SQSSenReportProperties(
     String region,
     String queueUrl,
     String endpoint){
}
