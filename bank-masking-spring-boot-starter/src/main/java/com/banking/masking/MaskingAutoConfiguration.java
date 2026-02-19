package com.banking.masking;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MaskingProperties.class)
@ConditionalOnProperty(prefix = "p11.masking", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MaskingAutoConfiguration {

    @Bean
    public MaskingService maskingService(MaskingProperties properties) {
        return new MaskingService(properties);
    }

    @Bean
    public MaskingAspect maskingAspect(MaskingService maskingService) {
        return new MaskingAspect(maskingService);
    }
}