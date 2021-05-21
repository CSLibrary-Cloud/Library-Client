package com.cslibrary.client.server

import com.cslibrary.client.configuration.ServerConfiguration
import com.cslibrary.client.data.request.*
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

    fun seatChangeCommunication(seatSelectRequest: SeatSelectRequest) : SeatSelectResponse {
        val httpEntity: HttpEntity<SeatSelectRequest> = getHttpEntityWithToken(seatSelectRequest)
        val seatSelectResponse: ResponseEntity<SeatSelectResponse> =
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/seat", HttpMethod.PUT, httpEntity)

        return seatSelectResponse.body!!
    }

    fun stateCommunication(stateChangeRequest: StateChangeRequest) : Void {
        val httpEntity: HttpEntity<StateChangeRequest> = getHttpEntityWithToken(stateChangeRequest)
        val changeStateResponse: ResponseEntity<Void> =
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/state", HttpMethod.PUT, httpEntity)

        return changeStateResponse.body!!
    }

    fun reportCommunication(reportRequest: ReportRequest) : Void {
        val httpEntity: HttpEntity<ReportRequest> = getHttpEntityWithToken(reportRequest)
        val reportResponse: ResponseEntity<Void> =
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/report", HttpMethod.POST, httpEntity)

        return reportResponse.body!!
    }

    fun extendTimeCommunication() : Void {
        val httpEntity: HttpEntity<Void> = getHttpEntityWithToken(null)
        val extendTimeResponse: ResponseEntity<Void> =
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/extend", HttpMethod.POST, httpEntity)

        return extendTimeResponse.body!!
    }

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