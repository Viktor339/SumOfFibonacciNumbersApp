package com.example.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Configuration
@Validated
@Data
@ConfigurationProperties(prefix = "client")
public class ClientProperties {

    @NotBlank
    private String host;

    @PositiveOrZero
    @NotNull
    private int port;
}
