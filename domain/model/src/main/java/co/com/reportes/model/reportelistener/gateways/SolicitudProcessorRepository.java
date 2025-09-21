package co.com.reportes.model.reportelistener.gateways;

import co.com.reportes.model.reportelistener.model.SolicitudModel;
import reactor.core.publisher.Mono;

public interface SolicitudProcessorRepository {

    Mono<SolicitudModel> addSolicitud(SolicitudModel solicitud);


}
