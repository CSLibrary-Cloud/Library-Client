package data

import data.request.LoginRequest
import data.request.RegisterRequest
import ServerManagement

data class User(
    var userId: String = "",
    var userPassword: String = "",
    var userName: String = "",
    var leftTime: Long = -1,
    var userPhoneNumber: String = "",
    var reservedSeatNumber: String = "",
    var userState: String = "",

    var serverManagement: ServerManagement      // added
) {
    fun signUp(userID: String, userPassword: String, userName: String, userPhoneNumber: String) {
        val regRequest = RegisterRequest(userID, userPassword, userName, userPhoneNumber)   // making Register Request

        serverManagement.signUpCommunication(regRequest)
    }

    fun login(userID: String, userPassword: String) {
        val logRequest = LoginRequest(userID, userPassword)     // login Register Request

        serverManagement.loginCommunication(logRequest)
    }
}
