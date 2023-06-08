package com.spring.customermanagementservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class InitConfig {

    @Value(value = "${enabled:true}")
    private boolean enabled;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo()).enable(enabled);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("REST API Documentation of Mini Project using Springboot",
                "REST API for customer management and transaction management",
                "v1",
                "",
                null,
                "indahsramonasari",
                "indahsramonasari.19@gmail.com",
                Collections.emptyList());
    }

}
