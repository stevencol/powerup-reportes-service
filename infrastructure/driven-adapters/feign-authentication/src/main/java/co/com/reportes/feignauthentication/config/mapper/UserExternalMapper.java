package co.com.reportes.feignauthentication.config.mapper;


import co.com.reportes.feignauthentication.config.model.ExternalUserModel;
import co.com.reportes.model.reportelistener.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserExternalMapper {

    UserModel userExternalToUserModel(ExternalUserModel userExternalDto);

    ExternalUserModel userModelToUserExternal(UserModel userModel);
}
