package com.cslibrary.client.ui

import com.cslibrary.client.data.request.LoginRequest
import com.cslibrary.client.data.response.LoginResponse
import com.cslibrary.client.server.ServerManagement
import org.springframework.stereotype.Component

@Component
class Login(
    private val shape: Shape,
    private val serverManagement: ServerManagement
) {

    fun loginUser(){
        shape.makeRec(3,"Login Page")

        print("ID : ")
        val userId: String = readLine()!!
        print("PW : ")
        val userPassword: String = readLine()!!

        //login 확인하기
        val loginResponse: LoginResponse = serverManagement.loginCommunication(
            LoginRequest(
                userId = userId,
                userPassword = userPassword
            )
        )

        //response로 받은 token
        if (loginResponse.userToken.isNotEmpty()) {
            println("Successfully Logged-In!")
        }
    }
}