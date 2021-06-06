package com.cslibrary.client.ui

import com.cslibrary.client.data.request.LoginRequest
import com.cslibrary.client.data.response.LoginResponse
import com.cslibrary.client.io.MainIO
import com.cslibrary.client.server.ServerManagement
import org.springframework.stereotype.Component

@Component
class Login(
    private val shape: Shape,
    private val serverManagement: ServerManagement,
    private val secondPage: SecondPage,
) {
    fun loginUser() {

        while (true) {
            MainIO.clearScreen()
            shape.makeRec(3, "Login Page")
            val userId: String = MainIO.getInputNormal("ID: ")
            val userPassword: String = MainIO.getInputPassword("PW: ")

            //login 확인하기
            val loginResponse: LoginResponse = serverManagement.loginCommunication(
                LoginRequest(
                    userId = userId,
                    userPassword = userPassword
                )
            ) ?: run {
                handleLoginFail()
                return
            }

            //response로 받은 token
            if (loginResponse.userToken.isNotEmpty()) {
                MainIO.printNormal("Successfully Logged-In!")
                secondPage.secondPage()
                return
            }else{
                handleLoginFail()
                return
            }
        }
    }

    private fun handleLoginFail() {
        MainIO.printError("Login Failed!\nGoing back to Main Page")
        MainIO.waitFor()
    }
}
