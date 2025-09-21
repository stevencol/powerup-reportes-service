package co.com.reportes.api.mapper;

import co.com.reportes.api.dto.SolicitudDto;
import co.com.reportes.model.reportelistener.model.SolicitudModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)

public interface SolicitudDtoMapper {

    SolicitudDto toDto (SolicitudModel solicitudModel);
}
