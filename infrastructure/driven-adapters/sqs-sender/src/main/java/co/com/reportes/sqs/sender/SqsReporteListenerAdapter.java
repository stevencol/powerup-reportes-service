package co.com.reportes.sqs.sender;

import co.com.reportes.model.reportelistener.gateways.ReporteListenerRepository;
import co.com.reportes.model.reportelistener.gateways.SolicitudProcessorRepository;
import co.com.reportes.model.reportelistener.model.SolicitudModel;
import com.google.gson.Gson;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
public class SqsReporteListenerAdapter implements ReporteListenerRepository {


    private final Gson gson;
    private final SolicitudProcessorRepository processorRepository;


    @Override
    @SqsListener("${aws.adapter.sqs.save.report.queueName}")
    public Mono<Void> listenerReporte(String mensaje) {


        SolicitudModel solicitudModel = gson.fromJson(mensaje, SolicitudModel.class);
        log.info("SolicitudModel: {}", solicitudModel);
        processorRepository.addSolicitud(solicitudModel)
                .doOnNext(solicitud -> log.info("Guardando solicitud recibida llamando al repo: {}", solicitud))
                .doOnError(error -> log.error("Error procesando solicitud", error))
                .subscribe();

        return Mono.empty();

    }


}

  /*  return processorRepository.procesar(solicitudModel).flatMap(solicitudModel1 -> {
            log.info("Solicitud guardada: {}", solicitudModel1);
            return Mono.just(solicitudModel1);
        });

       */