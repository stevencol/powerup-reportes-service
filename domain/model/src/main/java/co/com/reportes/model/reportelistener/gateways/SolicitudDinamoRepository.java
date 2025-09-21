package co.com.reportes.model.reportelistener.gateways;

import co.com.reportes.model.reportelistener.model.SolicitudModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface SolicitudDinamoRepository {


    public Mono<SolicitudModel> addSolicitud(SolicitudModel solicitud);

    public Flux<SolicitudModel> getAll();
}
