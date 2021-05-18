package com.cslibrary.client.server

import com.cslibrary.client.configuration.ServerConfiguration
import com.cslibrary.client.data.request.LoginRequest
import com.cslibrary.client.data.request.RegisterRequest
import com.cslibrary.client.data.request.SeatSelectRequest
import com.cslibrary.client.data.response.LoginResponse
import com.cslibrary.client.data.response.RegisterResponse
import com.cslibrary.client.data.response.SeatResponse
import com.cslibrary.client.data.response.SeatSelectResponse
import org.apache.coyote.Response
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
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

    fun getSeatInformation(): List<SeatResponse> {
        val httpEntity: HttpEntity<Void> = getHttpEntityWithToken(null)
        val seatResponse: ResponseEntity<List<SeatResponse>> =
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/seat", HttpMethod.GET, httpEntity)

        return seatResponse.body!!
    }

    //selecting seat
    fun seatSelectCommunication(seatSelectRequest: SeatSelectRequest) : SeatSelectResponse {
        val httpEntity: HttpEntity<SeatSelectRequest> = getHttpEntityWithToken(seatSelectRequest)
        val seatSelectResponse: ResponseEntity<SeatSelectResponse> =
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/seat", HttpMethod.POST, httpEntity)

        return seatSelectResponse.body!!
    }

    /**
     * TODO: WE NEED TO CHANGE SEAT
     * how to distinguish between selection & change?
     */

    /**
     * returns httpEntity with token applied.
     * add headerEntity if there is any body to sent to server.
     * [headerEntity = HTTP Body to send]
     */
    private inline fun <reified T> getHttpEntityWithToken(httpBody: T?): HttpEntity<T> {
        val httpHeaders: HttpHeaders = HttpHeaders().apply {
            put("X-AUTH-TOKEN", mutableListOf(loginToken))
        }

        return HttpEntity(httpBody, httpHeaders)
    }
}