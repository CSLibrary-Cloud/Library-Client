package com.libraryclient.libraryclient.ui

import org.springframework.stereotype.Component
import java.util.*

@Component
class MainPage(
    private val shape: Shape,
    private val login: Login,
    private val register: Register
) {

    fun mainpage() {

        val scanner : Scanner = Scanner(System.`in`)
        while(true){
            shape.makeRec(5,"Library System")
            shape.makeRec(3, "Main Page")
            println("1. Login\n2. Register\n3. Finish")
            print("Choose the number : ")
            val ch = scanner.next()
            when(ch!!) {
                "1" -> {
                    println("Login Selected")
                    login.loginUser()
                }
                "2"->{
                    println("Register Selected")
                    register.registerUser()
                }
                "3"->{
                    println("Finished")
                    return
                }
            }
        }
    }
}