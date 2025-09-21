package co.com.reportes.feignauthentication.config;

import co.com.reportes.feignauthentication.config.mapper.UserExternalMapper;
import co.com.reportes.model.reportelistener.gateways.UserExternalRepository;
import co.com.reportes.model.reportelistener.model.UserModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserExternalFeignAdapter implements UserExternalRepository {

    private final UserExternalFeign userExternalFeign;
    private final UserExternalMapper userExternalMapper;

    private final String authToken;

    public UserExternalFeignAdapter(@Value("${jwt.system.key}") String authToken, UserExternalFeign userExternalFeign, UserExternalMapper userExternalMapper) {
        this.authToken = authToken;
        this.userExternalFeign = userExternalFeign;
        this.userExternalMapper = userExternalMapper;
    }


    @Override
    public Mono<List<UserModel>> getUsersAdmins() {
        return userExternalFeign.getUsersAdmins("Bearer " + authToken).flatMap(

                resposne -> {
                    List<UserModel> userModels = resposne.data().stream()
                            .map(userExternalMapper::userExternalToUserModel)
                            .toList();
                    return Mono.just(userModels);
                }
        );
    }


}
