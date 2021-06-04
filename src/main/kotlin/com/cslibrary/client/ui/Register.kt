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

        while(true){
            clearScreen()
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

            if(userId == null || userPassword == null || userName == null || userPhoneNumber == null){
                print("입력이 되지 않았습니다. 다시 입력해주세요.")
            }
            else{
                // Register 하기
                val registerResponse: RegisterResponse? = serverManagement.signUpCommunication(
                    RegisterRequest(
                        userId = userId,
                        userPassword = userPassword,
                        userName = userName,
                        userPhoneNumber = userPhoneNumber
                    )
                )

                if (registerResponse?.registeredId!!.isNotEmpty()) {
                    println("Successfully registered with: ${registerResponse?.registeredId}")
                }else{
                    println("Register Failed!\nGoind back to Main Page")
                    return
                }
            }
        }
    }
    private fun clearScreen() {
        print("\u001B[H\u001B[2J")
        System.out.flush()
    }
}