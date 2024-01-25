package com.citi.WebConfiguratorService.common;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;

@Configuration
public class CoercionConfiguration {
	
	    @Bean
	    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
	        return builder -> builder.postConfigurer(objectMapper -> {
	            objectMapper.coercionConfigFor(LogicalType.POJO)
	                    .setCoercion(CoercionInputShape.String, CoercionAction.AsNull);
	        });
	    }
	}


