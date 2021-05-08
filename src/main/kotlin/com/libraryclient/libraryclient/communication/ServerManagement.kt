package com.libraryclient.libraryclient.communication

import com.cslibrary.library.data.dto.response.LoginResponse
import com.cslibrary.library.data.dto.response.RegisterResponse
import com.libraryclient.libraryclient.data.request.LoginRequest
import com.libraryclient.libraryclient.data.request.RegisterRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity


class ServerManagement (
    var serverAddress: String = "",     //TODO server Address needed
    var restTemplate: RestTemplate
) {

    fun signUpCommunication(registerRequest: RegisterRequest) : RegisterResponse {
        val registerResponse: ResponseEntity<RegisterResponse> =
            restTemplate.postForEntity(serverAddress, registerRequest, RegisterResponse::class)
        return registerResponse.body!!
    }

    fun loginCommunication(loginRequest: LoginRequest) : LoginResponse {
        val loginResponse: ResponseEntity<LoginResponse> =
            restTemplate.postForEntity(serverAddress, loginRequest, LoginResponse::class)
        return loginResponse.body!!
    }
}