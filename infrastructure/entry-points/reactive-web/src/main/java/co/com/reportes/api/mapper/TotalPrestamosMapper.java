package co.com.reportes.api.mapper;

import co.com.reportes.api.dto.ReporteDto;
import co.com.reportes.model.reportelistener.model.ReporteListenerModel;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TotalPrestamosMapper {

    ReporteDto toDto(ReporteListenerModel model);
}
