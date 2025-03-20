//package com.a702.finafanbe.global.config;
//
//import io.micrometer.core.instrument.MeterRegistry;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class MetricsConfig {
//
//    @Value("${spring.application.name}")
//    private String applicationName;
//
//    @Value("${spring.profiles.active:unknown}")
//    private String activeProfile;
//
//    @Bean
//    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
//        return registry -> registry.config()
//            .commonTags("application", applicationName)
//            .commonTags("environment", activeProfile);
//    }
//}