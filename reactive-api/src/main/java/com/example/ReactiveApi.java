package com.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Documentation OHM APIs v1.0"))
public class ReactiveApi {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApi.class, args);
    }

}