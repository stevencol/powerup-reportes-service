package co.com.reportes.model.reportelistener.gateways;


import co.com.reportes.model.reportelistener.model.ReporteSendReportModel;
import reactor.core.publisher.Mono;

public interface SqsSenderReportRepository {

    Mono<Void> senReportDaly(ReporteSendReportModel reporteModel);
}
