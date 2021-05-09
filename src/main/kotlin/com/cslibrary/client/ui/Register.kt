package com.cslibrary.client.ui

import com.cslibrary.client.data.request.RegisterRequest
import com.cslibrary.client.data.response.RegisterResponse
import com.cslibrary.client.server.ServerManagement
import org.springframework.stereotype.Component

@Component
class Register(
    private val shape: Shape,
    private val serverManagement: ServerManagement
) {

    fun registerUser(){
        shape.makeRec(3,"Register Page")
        println("Enter your Info")
        print("ID : ")
        val userId: String = readLine()!!
        print("Password : ")
        val userPassword: String = readLine()!!
        print("userName : ")
        val userName: String = readLine()!!
        print("PhoneNumber : ")
        val userPhoneNumber: String = readLine()!!

        // Register
        val registerResponse: RegisterResponse = serverManagement.signUpCommunication(
            RegisterRequest(
                userId = userId,
                userPassword = userPassword,
                userName = userName,
                userPhoneNumber = userPhoneNumber
            )
        )
        println("Successfully registered with: ${registerResponse.registeredId}")
    }

}