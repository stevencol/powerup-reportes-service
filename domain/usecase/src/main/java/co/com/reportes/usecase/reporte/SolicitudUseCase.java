package co.com.reportes.usecase.reporte;

import co.com.reportes.model.reportelistener.gateways.SolicitudDinamoRepository;
import co.com.reportes.model.reportelistener.gateways.SolicitudProcessorRepository;
import co.com.reportes.model.reportelistener.model.SolicitudModel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
public class SolicitudUseCase implements SolicitudProcessorRepository {

    private final SolicitudDinamoRepository solicitudDinamoRepository;

    public final Sinks.Many<SolicitudModel> sink = Sinks.many().multicast().onBackpressureBuffer();

    @Override
    public Mono<SolicitudModel> addSolicitud(SolicitudModel solicitud) {
        return solicitudDinamoRepository.addSolicitud(solicitud)
                .doOnNext(sink::tryEmitNext);
    }

    public Flux<SolicitudModel> getAllApproved() {
        return Flux.merge(
                solicitudDinamoRepository.getAll(),
                sink.asFlux()
        );
    }

}
