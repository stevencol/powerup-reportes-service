package co.com.reportes.api.helper;

import co.com.reportes.api.dto.ApiResponseDto;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


public class ResponseHelper {

    private ResponseHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> Mono<ServerResponse> responseHelper(ApiResponseDto<T> apiResponseDto) {
        return ServerResponse.status(apiResponseDto.status())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(apiResponseDto));
    }


}
