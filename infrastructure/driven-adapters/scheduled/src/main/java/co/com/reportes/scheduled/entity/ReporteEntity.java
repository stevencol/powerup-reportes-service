package co.com.reportes.scheduled.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ReporteEntity {
    private int totalPrestamos;
    private BigDecimal valorTotalPrestamos;
    private List<UserEntity> users;


}
