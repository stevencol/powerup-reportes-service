package co.com.reportes.model.reportelistener.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ReporteListenerModel {

    private int totalPrestamos;
    private BigDecimal valorTotalPrestamos;
}
