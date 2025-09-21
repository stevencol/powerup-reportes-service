package co.com.reportes.usecase.reporte;

import co.com.reportes.model.reportelistener.gateways.ReporteDynamoRepository;
import co.com.reportes.model.reportelistener.gateways.SolicitudDinamoRepository;
import co.com.reportes.model.reportelistener.model.ReporteListenerModel;
import co.com.reportes.model.reportelistener.model.SolicitudModel;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ReporteDynamoUseCase implements ReporteDynamoRepository {


    private final SolicitudDinamoRepository solicitudDinamoRepository;

    private final Sinks.Many<SolicitudModel> sink = Sinks.many().multicast().onBackpressureBuffer();


    @Override
    public Flux<ReporteListenerModel> getLoanSummary() {


        Mono<ReporteListenerModel> initialTotals = solicitudDinamoRepository.getAll()
                .reduce(new ReporteListenerModel(0, BigDecimal.ZERO), (report, solicitud) -> {
                    report.setTotalPrestamos(report.getTotalPrestamos() + 1);
                    report.setValorTotalPrestamos(report.getValorTotalPrestamos().add(solicitud.getMount()));
                    return report;
                });


        return initialTotals.flatMapMany(initial ->
                sink.asFlux()
                        .scan(initial, (report, solicitud) -> {
                            report.setTotalPrestamos(report.getTotalPrestamos() + 1);
                            report.setValorTotalPrestamos(report.getValorTotalPrestamos().add(solicitud.getMount()));
                            return report;
                        })
                        .startWith(initial)
        );
    }


}
