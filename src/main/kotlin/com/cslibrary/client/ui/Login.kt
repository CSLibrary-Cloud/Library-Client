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
            shape.makeRec(3, "로그인 메뉴")
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
                MainIO.printNormal("로그인이 성공적으로 수행되었습니다!")
                secondPage.secondPage()
                return
            }else{
                handleLoginFail()
                return
            }
        }
    }

    private fun handleLoginFail() {
        MainIO.printError("로그인에 실패하였습니다!")
        MainIO.printError("메인 페이지로 돌아갑니다..")
        MainIO.waitFor()
    }
}
