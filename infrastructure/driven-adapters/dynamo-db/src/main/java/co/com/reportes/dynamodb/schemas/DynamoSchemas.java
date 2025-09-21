package co.com.reportes.dynamodb.schemas;

import co.com.reportes.dynamodb.SolicitudEntity;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Configuration
public class DynamoSchemas {


    public static final TableSchema<SolicitudEntity> SOLICITUD_SCHEMA =
            TableSchema.fromBean(SolicitudEntity.class);
}
