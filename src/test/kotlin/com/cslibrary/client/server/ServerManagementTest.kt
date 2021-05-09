package com.cslibrary.client.server

import com.cslibrary.client.data.request.LoginRequest
import com.cslibrary.client.data.request.RegisterRequest
import com.cslibrary.client.data.response.LoginResponse
import com.cslibrary.client.data.response.RegisterResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class ServerManagementTest {

    @Autowired
    private lateinit var serverManagement: ServerManagement

    @Test
    fun is_signUpCommunication_works_well() {
        val mockRegisterRequest: RegisterRequest = RegisterRequest(
            userId = "kangdroid",
            userName = "kangdroid",
            userPassword = "kangdroid",
            userPhoneNumber = "010-xxxx-xxxx"
        )

        val registerResponse: RegisterResponse = serverManagement.signUpCommunication(mockRegisterRequest)

        assertThat(registerResponse.registeredId).isEqualTo(mockRegisterRequest.userId)
    }

    @Test
    fun is_loginCommunication_works_well() {
        val mockRegisterRequest: RegisterRequest = RegisterRequest(
            userId = "kangdroid",
            userName = "kangdroid",
            userPassword = "kangdroid",
            userPhoneNumber = "010-xxxx-xxxx"
        )
        serverManagement.signUpCommunication(mockRegisterRequest)

        val loginRequest: LoginRequest = LoginRequest(
            userId = "kangdroid",
            userPassword = "kangdroid"
        )

        val loginResponse: LoginResponse = serverManagement.loginCommunication(loginRequest)
        assertThat(loginResponse.userToken).isNotEqualTo("")
    }
}