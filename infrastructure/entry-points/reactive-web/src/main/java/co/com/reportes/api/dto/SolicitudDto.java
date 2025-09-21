package co.com.reportes.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SolicitudDto(

        Long id,
        String  userDocumentNumber,
        BigDecimal mount,
        Integer termInMonths,
        String loanType,
        String status,
        double interestRate

) {
}
