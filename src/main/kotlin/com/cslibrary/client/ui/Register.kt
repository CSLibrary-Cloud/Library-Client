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
        val id: String = readLine()!!
        print("Password : ")
        val pw: String = readLine()!!
        print("userName : ")
        val name: String = readLine()!!
        print("PhoneNumber : ")
        val phone: String = readLine()!!

    }

}