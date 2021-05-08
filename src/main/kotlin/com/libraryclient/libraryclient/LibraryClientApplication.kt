package com.libraryclient.libraryclient

import com.libraryclient.libraryclient.configuration.ServerConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(ServerConfiguration::class)
@SpringBootApplication
class LibraryClientApplication

fun main(args: Array<String>) {
	runApplication<LibraryClientApplication>(*args)
}
