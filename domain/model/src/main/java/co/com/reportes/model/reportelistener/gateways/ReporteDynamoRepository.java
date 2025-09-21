package co.com.reportes.model.reportelistener.gateways;

import co.com.reportes.model.reportelistener.model.ReporteListenerModel;
import reactor.core.publisher.Flux;


public interface ReporteDynamoRepository {

    public Flux<ReporteListenerModel> getLoanSummary();



}
