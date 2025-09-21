package co.com.reportes.dynamodb.mappper;

import co.com.reportes.dynamodb.SolicitudEntity;
import co.com.reportes.model.reportelistener.model.SolicitudModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)


public interface SolicitudMapper {

    SolicitudEntity toEntity(SolicitudModel solicitudModel);

    SolicitudModel toModel(SolicitudEntity solicitudEntity);
}

