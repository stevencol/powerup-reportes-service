package co.com.reportes.scheduled.mapper;

import co.com.reportes.model.reportelistener.model.UserModel;
import co.com.reportes.scheduled.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserEntity toEntity(UserModel userModel);

    List<UserEntity> toEntityList(List<UserModel> userModel);


}
