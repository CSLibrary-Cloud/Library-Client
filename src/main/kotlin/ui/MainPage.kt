package ui

import org.springframework.boot.runApplication
import ui.Login
import ui.Register
import ui.Shape
import java.util.*

class MainPage {

    fun mainpage() {

        val scanner : Scanner = Scanner(System.`in`)
        val shape = Shape()
        while(true){

            shape.makeRec(5,"Library System")
            shape.makeRec(3, "Main Page")
            println("1. Login\n2. Register\n3. Finish")
            print("Choose the number : ")
            val ch = scanner.next()
            when(ch!!) {
                "1" -> {
                    println("Login Selected")
                    val login = Login()
                    login.loginUser()
                }
                "2"->{
                    println("Register Selected")
                    val register = Register()
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