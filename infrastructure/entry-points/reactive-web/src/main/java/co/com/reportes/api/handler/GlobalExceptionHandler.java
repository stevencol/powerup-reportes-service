package co.com.reportes.api.handler;


import co.com.reportes.api.dto.ApiResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Order(-2)
public class GlobalExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.info(ex.getClass().getName());
        var response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponseDto<?> body;
        log.info(ex.getClass().getName());
        switch (ex) {

            default -> body = new ApiResponseDto<>(status.toString(), String.valueOf(ex), status);
        }
        response.setStatusCode(status);
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(body);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));

        } catch (Exception e) {
            return response.setComplete();
        }
    }
}

