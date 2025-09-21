package co.com.reportes.sqs.sender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix = "aws.adapter.sqs.save.report")
public record SQSReporteListenerProperties(
     String region,
     String queueUrl,
     String endpoint){
}
