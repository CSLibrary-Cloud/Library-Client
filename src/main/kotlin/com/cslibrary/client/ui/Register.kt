package com.cslibrary.client.ui

import com.cslibrary.client.data.request.RegisterRequest
import com.cslibrary.client.data.response.RegisterResponse
import com.cslibrary.client.server.ServerManagement
import org.springframework.stereotype.Component
import java.util.*

@Component
class Register(
    private val shape: Shape,
    private val serverManagement: ServerManagement
) {
    val scanner: Scanner = Scanner(System.`in`)
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

                if(registerResponse == null){
                    println("Register Failed!\nGoing back to Main Page")
                    println("\nPress enter key to continue..")
                    scanner.nextLine()
                    return
                }

                if (registerResponse?.registeredId!!.isNotEmpty()) {
                    println("Successfully registered with: ${registerResponse?.registeredId}")
                    return
                }else{
                    println("Register Failed!\nGoind back to Main Page")
                    println("\nPress enter key to continue..")
                    scanner.nextLine()
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