package co.com.reportes.model.reportelistener.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ReporteSendReportModel {

    private int totalPrestamos;
    private BigDecimal valorTotalPrestamos;
    private List<UserModel> users;
}
