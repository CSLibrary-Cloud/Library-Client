package com.libraryclient.libraryclient.ui

import org.springframework.stereotype.Component

@Component
class Login(
    private val shape: Shape
) {

    fun loginUser(){
        shape.makeRec(3,"Login Page")

        print("ID : ")
        val id = readLine()
        print("PW : ")
        val pw = readLine()

        //login 확인하기

        //response로 받은 token

    }
}