package co.com.reportes.sqs.sender;

import co.com.reportes.model.reportelistener.gateways.SqsSenderReportRepository;
import co.com.reportes.model.reportelistener.model.ReporteSendReportModel;
import com.google.gson.Gson;
import exceptions.SqsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import static constants.MessageExceptions.*;
import static constants.MessagesInfo.*;
@Slf4j
@Component
public class SqsSendReportAdapter  implements SqsSenderReportRepository {

    private final SqsAsyncClient sqsClient;
    private final Gson gson;
    private final String queueUrl;

    public SqsSendReportAdapter(SqsAsyncClient sqsClient, Gson gson, @Value("${aws.adapter.sqs.sender.report.queueUrl}") String queueUrl) {
        this.sqsClient = sqsClient;
        this.gson = gson;
        this.queueUrl = queueUrl;
    }

    @Override
    public Mono<Void> senReportDaly(ReporteSendReportModel reporteModel) {
        String messageJson = gson.toJson(reporteModel);

        log.info("JSON a enviar a SQS: {}", messageJson);

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageJson)
                .build();

        return Mono.fromFuture(() -> sqsClient.sendMessage(request))
                .doOnNext(resp ->
                        log.info(MSG_SQS_SUCCESS)
                )
                .onErrorMap(ex -> {
                    log.error(MSG_SQS_MESSAGE_ERROR, ex);
                    return new SqsException(String.format(MSG_SQS_MESSAGE_ERROR, reporteModel));

                }).then();

    }
}
