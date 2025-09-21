package co.com.reportes.feignauthentication.config.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionService;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients(basePackages = "co.com.reportes")
@Configuration
public class FeignConversionConfig {

    @Bean
    public ConversionService feignConversionService() {
        return new FormattingConversionService();
    }
}
