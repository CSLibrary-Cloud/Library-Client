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
        shape.makeRec(3,"Register Page")
        MainIO.printNormal("Enter your information")

        // Register 하기
        (serverManagement.signUpCommunication(getRegisterRequest()) ?: run {
            MainIO.printError("Register Failed!\nGoing back to Main Page")
            MainIO.waitFor()
            return
        }).also {
            MainIO.printNormal("Successfully registered with: ${it.registeredId}")
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
            message = "Wrong PW Input: Password length should be >=8 and should contains at least one or more special letters."
        )
        val userName: String = getUserAndValidateInput({MainIO.getInputNormal("User Name: ")})
        val userPhoneNumber: String = getUserAndValidateInput(
            toExecute = {MainIO.getInputNormal("Phone Number: ")},
            additionalStep = { inputString ->
                phoneRegex.matches(inputString)
            },
            message = "Wrong Phone Number input. For example, input like: 010-xxxx-xxxx"
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