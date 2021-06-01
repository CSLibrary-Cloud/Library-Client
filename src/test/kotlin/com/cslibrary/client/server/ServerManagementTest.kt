package com.cslibrary.client.server

import com.cslibrary.client.data.request.*
import com.cslibrary.client.data.response.*
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

        val registerResponse: RegisterResponse? = serverManagement.signUpCommunication(mockRegisterRequest)

        assertThat(registerResponse?.registeredId).isEqualTo(mockRegisterRequest.userId)
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

        val loginResponse: LoginResponse? = serverManagement.loginCommunication(loginRequest)

        assertThat(loginResponse?.userToken).isNotEqualTo("")
    }

    @Test
    fun is_getSeatInformation_works_well() {
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
        serverManagement.loginCommunication(loginRequest)

        val seatResponse: List<SeatResponse>? = serverManagement.getSeatInformation()

        assertThat(seatResponse?.size).isEqualTo(30)
    }

    @Test
    fun is_seatSelectCommunication_works_well() {
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
        serverManagement.loginCommunication(loginRequest)

        val seatSelectRequest: SeatSelectRequest = SeatSelectRequest(
            seatNumber = 1
        )

        val seatSelectResponse : UserLeftTimeResponse? = serverManagement.seatSelectCommunication(seatSelectRequest)

        assertThat(seatSelectResponse?.reservedSeat?.reservedSeatNumber).isEqualTo(seatSelectRequest.seatNumber)
    }

    @Test
    fun is_seatChangeCommunication_works_well() {
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
        serverManagement.loginCommunication(loginRequest)

        val seatSelectRequest: SeatSelectRequest = SeatSelectRequest(
            seatNumber = 1
        )

        val seatSelectResponse: UserLeftTimeResponse? = serverManagement.seatSelectCommunication(seatSelectRequest)

        val seatChangeRequest: SeatSelectRequest = SeatSelectRequest(
            seatNumber = 3
        )

        val seatChangeResponse: SeatSelectResponse? = serverManagement.seatChangeCommunication(seatChangeRequest)
        assertThat(seatChangeResponse?.reservedSeatNumber).isEqualTo(seatChangeRequest.seatNumber)
    }

    @Test
    fun is_saveLeftTimeCommunication_works_well() {
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
        serverManagement.loginCommunication(loginRequest)

        val seatSelectRequest: SeatSelectRequest = SeatSelectRequest(
            seatNumber = 1
        )
        serverManagement.seatSelectCommunication(seatSelectRequest)

        val mockSaveLeftTime: SaveLeftTime = SaveLeftTime(
            leftTime = 100
        )

        val saveTimeResponse: SaveLeftTimeResponse? = serverManagement.saveLeftTimeCommunication(mockSaveLeftTime)
        if (saveTimeResponse != null) {
            assertThat(saveTimeResponse.leaderBoardList[0].totalStudyTime).isEqualTo(60*60*3 - mockSaveLeftTime.leftTime)
        }
    }
}