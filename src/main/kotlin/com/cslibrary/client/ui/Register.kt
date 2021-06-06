package com.cslibrary.client.ui

import com.cslibrary.client.data.request.RegisterRequest
import com.cslibrary.client.data.response.RegisterResponse
import com.cslibrary.client.io.MainIO
import com.cslibrary.client.server.ServerManagement
import org.springframework.stereotype.Component
import java.util.*

@Component
class Register(
    private val shape: Shape,
    private val serverManagement: ServerManagement
) {
    private val passwordRegex: Regex = """^(?=.*[@#$%!\-_?&])(?=\S+$).*""".toRegex()
    private val phoneRegex: Regex = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$".toRegex()

    fun registerUser(){
        MainIO.clearScreen()
        shape.makeRec(3,"회원가입 메뉴")
        MainIO.printNormal("정보를 입력해 주세요.")

        // Register 하기
        (serverManagement.signUpCommunication(getRegisterRequest()) ?: run {
            MainIO.printError("회원가입에 실패하였습니다!")
            MainIO.printError("메인 메뉴로 돌아갑니다..")
            MainIO.waitFor()
            return
        }).also {
            MainIO.printNormal("${it.registeredId} 아이디로 회원가입이 완료되었습니다.")
            MainIO.waitFor()
        }
    }

    // Get Input for register and return registerRequest
    private fun getRegisterRequest(): RegisterRequest {
        val userId: String = getUserAndValidateInput({MainIO.getInputNormal("ID: ")})
        val userPassword: String = getUserAndValidateInput(
            toExecute = {MainIO.getInputPassword("Password: ")},
            additionalStep = { inputString ->
                (inputString.length >= 8) && (passwordRegex.matches(inputString))
            },
            message = "비밀번호는 8자 이상이어야 되며, 특수문자 @, #, $, %, ! 중 하나를 포함해야 됩니다!"
        )

        val userPasswordCheck: String = getUserAndValidateInput(
            toExecute = {MainIO.getInputPassword("Input password again: ")},
            additionalStep = { inputString ->
                (inputString == userPassword)
            },
            message = "패스워드가 서로 맞지 않습니다!"
        )

        val userName: String = getUserAndValidateInput({MainIO.getInputNormal("User Name: ")})
        val userPhoneNumber: String = getUserAndValidateInput(
            toExecute = {MainIO.getInputNormal("Phone Number: ")},
            additionalStep = { inputString ->
                phoneRegex.matches(inputString)
            },
            message = "핸드폰 번호 입력이 잘못되었습니다. 다음과 같이 입력해 주세요: 010-xxxx-xxxx"
        )

        return RegisterRequest(
            userId = userId,
            userPassword = userPassword,
            userName = userName,
            userPhoneNumber = userPhoneNumber
        )
    }

    // Input and validate
    private fun getUserAndValidateInput(toExecute: () -> String, additionalStep: ((String) -> Boolean)? = null, message: String = "Wrong Input."): String {
        var userId: String
        do {
            userId = toExecute()
        } while (!inputValidation(userId, additionalStep = additionalStep, message = message))

        return userId
    }

    // True when passed
    private fun inputValidation(
        input: String,
        additionalStep: ((String) -> Boolean)? = null,
        message: String = "Wrong Input."
    ): Boolean {
        val additionalBoolean: Boolean = additionalStep?.invoke(input) ?: true
        val condition: Boolean = (additionalBoolean) && (input.isNotEmpty())

        if (!condition) {
            MainIO.printError(message)
        }
        return condition
    }
}