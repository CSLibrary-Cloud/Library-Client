package com.cslibrary.client.ui

import com.cslibrary.client.data.request.LoginRequest
import com.cslibrary.client.data.response.LoginResponse
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
            clearScreen()
            shape.makeRec(3, "Login Page")
            print("ID : ")
            val userId: String = readLine()!!
            print("PW : ")
            val userPassword: String = readLine()!!

            if (userId == null || userPassword == null) {
                print("입력이 되지 않았습니다. 다시 입력해주세요.")
            } else {

                //login 확인하기
                val loginResponse: LoginResponse? = serverManagement.loginCommunication(
                    LoginRequest(
                        userId = userId,
                        userPassword = userPassword
                    )
                )

                if(loginResponse == null){
                    println("Login Failed!\nGoing back to Main Page")
                    return
                }

                //response로 받은 token
                else {
                    if (loginResponse.userToken.isNotEmpty()) {
                        println("Successfully Logged-In!")
                        secondPage.secondPage()
                        return
                    }else{
                        println("Login Failed!\nGoing back to Main Page")
                        return
                    }
                }
            }
        }
    }
    private fun clearScreen() {
        print("\u001B[H\u001B[2J")
        System.out.flush()
    }
}
