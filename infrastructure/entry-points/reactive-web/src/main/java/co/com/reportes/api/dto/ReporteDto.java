package co.com.reportes.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReporteDto {
    private int totalPrestamos;
    private BigDecimal valorTotalPrestamos;
}
