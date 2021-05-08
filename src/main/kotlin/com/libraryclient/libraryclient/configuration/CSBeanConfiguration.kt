package com.libraryclient.libraryclient.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class CSBeanConfiguration {
    // Scope: Singleton
    @Bean
    fun getRestTemplate(): RestTemplate = RestTemplate()
}