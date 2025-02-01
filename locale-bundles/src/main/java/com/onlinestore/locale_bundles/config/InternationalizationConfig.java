package com.onlinestore.locale_bundles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class InternationalizationConfig {
//    /** If the i18n is in the multimodule project root, and not in module resources folder*/
//    @Value("${PROJECT_ROOT}")
//    private String projectRoot;

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        /** If the i18n is in the multimodule project root, and not in module resources folder*/
        //messageSource.setBasename("file:" + projectRoot + "/i18n/messages");
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setCacheSeconds(3600);  // Reload every hour
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
