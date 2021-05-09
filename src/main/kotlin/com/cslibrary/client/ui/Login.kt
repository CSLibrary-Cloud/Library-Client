package com.cslibrary.client.ui

import org.springframework.stereotype.Component

@Component
class Login(
    private val shape: Shape
) {

    fun loginUser(){
        shape.makeRec(3,"Login Page")

        print("ID : ")
        val userId: String = readLine()!!
        print("PW : ")
        val userPassword: String = readLine()!!

        //login 확인하기

        //response로 받은 token

    }
}