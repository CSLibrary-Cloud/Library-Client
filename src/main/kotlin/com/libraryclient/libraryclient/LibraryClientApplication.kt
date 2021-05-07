package com.libraryclient.libraryclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LibraryClientApplication

fun main(args: Array<String>) {
	runApplication<LibraryClientApplication>(*args)
}
