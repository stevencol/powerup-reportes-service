package co.com.reportes.dynamodb;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;

@Data
@DynamoDbBean
public class SolicitudEntity {

    private Long id;
    private String userDocumentNumber;
    private BigDecimal mount;
    private Integer termInMonths;
    private String loanType;
    private String status;
    private double interestRate;

    @DynamoDbPartitionKey
    public Long getId() {
        return id;
    }

    @DynamoDbSortKey
    public String getUserDocumentNumber() {
        return userDocumentNumber;
    }


}
