package com.libraryclient.libraryclient.communication

import com.cslibrary.library.data.dto.response.LoginResponse
import com.cslibrary.library.data.dto.response.RegisterResponse
import com.libraryclient.libraryclient.configuration.ServerConfiguration
import com.libraryclient.libraryclient.data.request.LoginRequest
import com.libraryclient.libraryclient.data.request.RegisterRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

@Component
class ServerManagement (
    private val serverConfiguration: ServerConfiguration,
    private val restTemplate: RestTemplate
) {

    fun signUpCommunication(registerRequest: RegisterRequest) : RegisterResponse {
        val registerResponse: ResponseEntity<RegisterResponse> =
            restTemplate.postForEntity(serverConfiguration.serverBaseAddress, registerRequest, RegisterResponse::class)
        return registerResponse.body!!
    }

    fun loginCommunication(loginRequest: LoginRequest) : LoginResponse {
        val loginResponse: ResponseEntity<LoginResponse> =
            restTemplate.postForEntity(serverConfiguration.serverBaseAddress, loginRequest, LoginResponse::class)
        return loginResponse.body!!
    }
}