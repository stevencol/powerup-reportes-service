package co.com.reportes.dynamodb.helper;


import co.com.reportes.dynamodb.SolicitudEntity;
import co.com.reportes.dynamodb.mappper.SolicitudMapper;
import co.com.reportes.dynamodb.schemas.DynamoSchemas;
import co.com.reportes.model.reportelistener.gateways.SolicitudDinamoRepository;
import co.com.reportes.model.reportelistener.model.SolicitudModel;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;


import java.util.List;


@Component
@Slf4j
public class SolicitudDinamoAdapter implements SolicitudDinamoRepository {

    private final DynamoDbEnhancedAsyncClient dynamoDbClient;
    private final SolicitudMapper mapper;
    private final DynamoDbAsyncTable<SolicitudEntity> table;
    private final Gson gson;

    public SolicitudDinamoAdapter(DynamoDbEnhancedAsyncClient dynamoDbClient,
                                  SolicitudMapper mapper, Gson gson) {
        this.dynamoDbClient = dynamoDbClient;
        this.mapper = mapper;
        this.table = dynamoDbClient.table("reportes", DynamoSchemas.SOLICITUD_SCHEMA);
        this.gson = gson;
    }


    @Override
    public Mono<SolicitudModel> addSolicitud(SolicitudModel solicitud) {
        log.info("Iniciando solicitud dinamo adapter");
        SolicitudEntity entity = mapper.toEntity(solicitud);
        log.info("Solicitud encontrada: {}", entity);

        return Mono.fromFuture(() -> table.putItem(entity))
                .thenReturn(solicitud);
    }

    @Override
    public Flux<SolicitudModel> getAll() {

        return Flux.from(table.scan())
                .flatMap(page -> Flux.fromIterable(page.items()))
                .map(mapper::toModel)
                .switchIfEmpty(Flux.empty());

    }
}