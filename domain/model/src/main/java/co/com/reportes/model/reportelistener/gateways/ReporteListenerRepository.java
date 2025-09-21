package co.com.reportes.model.reportelistener.gateways;

import reactor.core.publisher.Mono;

public interface ReporteListenerRepository {

    public Mono<Void> listenerReporte(String mensaje);
}
