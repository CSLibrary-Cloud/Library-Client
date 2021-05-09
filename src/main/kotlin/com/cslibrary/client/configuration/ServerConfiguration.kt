package com.cslibrary.client.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import javax.annotation.PostConstruct

@ConstructorBinding
@ConfigurationProperties("cs-library")
class ServerConfiguration(
    private val serverScheme: String,
    private val serverAddress: String,
    private val serverPort: String
) {
    lateinit var serverBaseAddress: String
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    fun initServerAddress() {
        serverBaseAddress = "$serverScheme://$serverAddress:$serverPort"
        logger.info("Initiated server address: $serverBaseAddress")
    }
}