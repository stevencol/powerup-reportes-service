package co.com.reportes.api.handler;

import co.com.reportes.api.dto.ReporteDto;
import co.com.reportes.api.dto.SolicitudDto;
import co.com.reportes.api.mapper.SolicitudDtoMapper;
import co.com.reportes.api.mapper.TotalPrestamosMapper;
import co.com.reportes.model.reportelistener.gateways.UserExternalRepository;
import co.com.reportes.usecase.reporte.ReporteDynamoUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class Handler {


    private final ReporteDynamoUseCase reporteUseCase;
    private final SolicitudDtoMapper solicitudDtoMapper;
    private final TotalPrestamosMapper totalPrestamosMapper;
    private final UserExternalRepository userExternalRepository;


    public Mono<ServerResponse> getLoanSummary(ServerRequest serverRequest) {

        Flux<ReporteDto> resumenFlux = reporteUseCase.getLoanSummary()
                .map(totalPrestamosMapper::toDto);

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(resumenFlux, SolicitudDto.class);
    }



    /*
    public Mono<ServerResponse> getLoanSummary(ServerRequest serverRequest) {
        return userExternalRepository.getUsersAdmins()
                .flatMap(admins ->
                        responseHelper(new ApiResponseDto<List<UserModel>>(admins, "Admins retrieved successfully", HttpStatus.OK))

                );
    }


     */


    /*
    public Mono<ServerResponse> getAllApproved(ServerRequest serverRequest) {
        Flux<SolicitudDto> dtoFlux = solicitudUseCase.getAllApproved()
                .map(solicitudDtoMapper::toDto);

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(dtoFlux, SolicitudDto.class);
    }

     */


}
