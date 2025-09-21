package co.com.reportes.feignauthentication.config;

import co.com.reportes.feignauthentication.config.model.ExternalApiResponseModel;
import co.com.reportes.feignauthentication.config.model.ExternalUserModel;import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

@ReactiveFeignClient(name = "users-service", url = "${USERS_SERVICE_URL}")
public interface UserExternalFeign {


    @GetMapping("/api/users/admins")
    Mono<ExternalApiResponseModel<List<ExternalUserModel>>> getUsersAdmins(@RequestHeader("Authorization") String token);
}
