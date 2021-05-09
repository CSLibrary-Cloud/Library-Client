package com.cslibrary.client.server

import com.cslibrary.client.configuration.ServerConfiguration
import com.cslibrary.client.data.request.LoginRequest
import com.cslibrary.client.data.request.RegisterRequest
import com.cslibrary.client.data.response.LoginResponse
import com.cslibrary.client.data.response.RegisterResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

@Component
class ServerManagement (
    private val serverConfiguration: ServerConfiguration,
    private val restTemplate: RestTemplate
) {
    // Login token storage
    private var loginToken: String? = null

    fun signUpCommunication(registerRequest: RegisterRequest) : RegisterResponse {
        val registerResponse: ResponseEntity<RegisterResponse> =
            restTemplate.postForEntity("${serverConfiguration.serverBaseAddress}/api/v1/user", registerRequest)
        return registerResponse.body!!
    }

    fun loginCommunication(loginRequest: LoginRequest) : LoginResponse {
        val loginResponse: ResponseEntity<LoginResponse> =
            restTemplate.postForEntity("${serverConfiguration.serverBaseAddress}/api/v1/login", loginRequest, LoginResponse::class)
        loginToken = loginResponse.body!!.userToken
        return loginResponse.body!!
    }
}