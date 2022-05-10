package com.example.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Duration;

@Configuration
@Validated
@Data
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {

    @NotNull
    private Duration timeToLive;

    @Positive
    private long maxSize;
}
