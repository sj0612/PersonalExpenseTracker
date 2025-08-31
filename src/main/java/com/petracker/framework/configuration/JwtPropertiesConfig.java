package com.petracker.framework.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jwt.properties")
public class JwtPropertiesConfig {
    // This class can be empty. Its purpose is to load the properties file.
}