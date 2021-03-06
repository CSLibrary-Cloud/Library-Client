package com.cslibrary.client.server

import com.cslibrary.client.configuration.ServerConfiguration
import com.cslibrary.client.data.request.*
import com.cslibrary.client.data.response.*
import com.cslibrary.client.io.MainIO
import com.cslibrary.library.error.ErrorResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.*

@Component
class ServerManagement (
    private val serverConfiguration: ServerConfiguration,
    private val restTemplate: RestTemplate
) {
    // Login token storage
    private var loginToken: String? = null

    // Exception will be added later
    fun signUpCommunication(registerRequest: RegisterRequest): RegisterResponse? {
        val response: ResponseEntity<String> = getResponseEntityInStringFormat {
            restTemplate.postForEntity("${serverConfiguration.serverBaseAddress}/api/v1/user", registerRequest)
        } ?: return null

        val mapper = jacksonObjectMapper()

        return mapper.readValue(response.body, RegisterResponse::class.java)
    }

    // Exception will be added later
    fun loginCommunication(loginRequest: LoginRequest) : LoginResponse? {
        val response: ResponseEntity<String> = getResponseEntityInStringFormat {
            restTemplate.postForEntity("${serverConfiguration.serverBaseAddress}/api/v1/login", loginRequest, LoginResponse::class)
        } ?: return null

        val loginResponse: LoginResponse = jacksonObjectMapper().readValue(response.body, LoginResponse::class.java)
        loginToken = loginResponse.userToken
        return loginResponse
    }

    fun getSeatInformation(): List<SeatResponse>? {
        val httpEntity: HttpEntity<Void> = getHttpEntityWithToken(null)
        val response: ResponseEntity<String> = getResponseEntityInStringFormat {
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/seat", HttpMethod.GET, httpEntity)
        } ?: return null

        val mapper = jacksonObjectMapper()
        return mapper.readValue(response.body, object : TypeReference<List<SeatResponse>>() {})
    }

    //selecting seat
    fun seatSelectCommunication(seatSelectRequest: SeatSelectRequest) : UserLeftTimeResponse? {
        val httpEntity: HttpEntity<SeatSelectRequest> = getHttpEntityWithToken(seatSelectRequest)
        val response: ResponseEntity<String> = getResponseEntityInStringFormat{
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/seat", HttpMethod.POST, httpEntity)
        } ?: return null

        val mapper = jacksonObjectMapper()

        //reservedSeat ??? leftTime ????????? ?????? ????????????.
        return mapper.readValue(response.body, UserLeftTimeResponse::class.java)
    }

    fun seatChangeCommunication(seatSelectRequest: SeatSelectRequest) : SeatSelectResponse? {
        val httpEntity: HttpEntity<SeatSelectRequest> = getHttpEntityWithToken(seatSelectRequest)
        val response: ResponseEntity<String> = getResponseEntityInStringFormat {
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/seat", HttpMethod.PUT, httpEntity)
        } ?: return null

        val mapper = jacksonObjectMapper()

        return mapper.readValue(response.body, SeatSelectResponse::class.java)
    }

    fun stateCommunication(stateChangeRequest: StateChangeRequest) {
        val httpEntity: HttpEntity<StateChangeRequest> = getHttpEntityWithToken(stateChangeRequest)
        val response: ResponseEntity<String>? = getResponseEntityInStringFormat {
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/state", HttpMethod.PUT, httpEntity)
        }
    }

    fun reportCommunication(reportRequest: ReportRequest) {
        val httpEntity: HttpEntity<ReportRequest> = getHttpEntityWithToken(reportRequest)
        val response: ResponseEntity<String>? = getResponseEntityInStringFormat {
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/report", HttpMethod.POST, httpEntity)
        }
    }

    fun saveLeftTimeCommunication(saveLeftTime: SaveLeftTime) : SaveLeftTimeResponse? {
        val httpEntity: HttpEntity<SaveLeftTime> = getHttpEntityWithToken(saveLeftTime)
        val response: ResponseEntity<String> = getResponseEntityInStringFormat {
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/user/time", HttpMethod.POST, httpEntity)
        } ?: return null

        // it has leaderBoardList & userNotification List
        val mapper = jacksonObjectMapper()

        return mapper.readValue(response.body, SaveLeftTimeResponse::class.java)
    }

    fun extendTime(time: Long): ExtendTimeResponse? {
        val httpEntity: HttpEntity<ExtendTimeRequest> = getHttpEntityWithToken(ExtendTimeRequest(time))
        val response: ResponseEntity<String> = getResponseEntityInStringFormat {
            restTemplate.exchange("${serverConfiguration.serverBaseAddress}/api/v1/user/time/extend", HttpMethod.POST, httpEntity)
        } ?: return null

        // it has leaderBoardList & userNotification List
        val mapper = jacksonObjectMapper()

        return mapper.readValue(response.body, ExtendTimeResponse::class.java)
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

    fun getResponseEntityInStringFormat(toExecute: () -> ResponseEntity<String>): ResponseEntity<String>? {
        return try {
            toExecute()
        } catch (resourceAccessException: ResourceAccessException) {
            MainIO.printError("Error communicating with server. Check server address and internet connection.")
            return null
        } catch (httpClientErrorException: HttpClientErrorException) {
            handleServerClientError(httpClientErrorException)
            return null
        } catch (httpServerErrorException: HttpServerErrorException) {
            handleServerClientError(httpServerErrorException)
            return null
        }
    }

    fun handleServerClientError(httpStatusCodeException: HttpStatusCodeException) {
        // With 4xx Codes!
        val body: String = httpStatusCodeException.responseBodyAsString

        // meaning error!
        val errorResponse: ErrorResponse = runCatching {
            jacksonObjectMapper().readValue(body, ErrorResponse::class.java)
        }.onFailure { innerIt ->
            // If body type is NOT matching with errorResponse
            MainIO.printError("Exception Occurred!")
            MainIO.printError(innerIt.message ?: "No message available")
        }.getOrNull() ?: return

        MainIO.printError("Server responded with: ${errorResponse.statusCode} - ${errorResponse.statusMessage}")
        MainIO.printError("Message is: ${errorResponse.errorMessage}") // errorMessage print ??? client ui ?????? ????????? ?????? ??????
    }
}