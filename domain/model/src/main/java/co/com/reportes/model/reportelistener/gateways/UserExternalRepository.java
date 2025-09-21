package co.com.reportes.model.reportelistener.gateways;

import co.com.reportes.model.reportelistener.model.UserModel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserExternalRepository {

    Mono<List<UserModel>> getUsersAdmins();
}
