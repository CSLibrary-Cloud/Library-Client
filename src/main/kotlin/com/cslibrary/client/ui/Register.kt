package com.cslibrary.client.ui

import org.springframework.stereotype.Component

@Component
class Register(
    private val shape: Shape
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

    }

}