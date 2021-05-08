package com.libraryclient.libraryclient.ui

import org.springframework.stereotype.Component

@Component
class Register(
    private val shape: Shape
) {

    fun registerUser(){
        shape.makeRec(3,"Register Page")
        println("Enter your Info")
        print("ID : ")
        val id = readLine()
        print("Password : ")
        val pw = readLine()
        print("userName : ")
        val name = readLine()
        print("PhoneNumber : ")
        val phone = readLine()

    }

}