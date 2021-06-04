package com.cslibrary.client.ui

import com.cslibrary.client.data.request.ReportRequest
import com.cslibrary.client.server.ServerManagement
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import kotlin.system.exitProcess

@Component
class MainPage(
    private val shape: Shape,
    private val login: Login,
    private val register: Register,
    private val serverManagement: ServerManagement
) {

    @PostConstruct
    fun initiateMainApplication() {
        if (System.getProperty("kdr.isTesting") != "test") {
            mainPage()
        }
    }

    private fun mainPage() {

        val scanner : Scanner = Scanner(System.`in`)
        while(true){
            clearScreen()
            shape.makeRec(5,"Library System")
            shape.makeRec(3, "Main Page")
            println("1. Login\n2. Register\n3. Report User\n4. Finish")
            print("Choose the number : ")
            val ch = scanner.nextLine()
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
                    shape.makeRec(3,"Report User")
                    println("| Message Title")
                    val title = scanner.nextLine()
                    println("| Message")
                    val message = scanner.nextLine()
                    serverManagement.reportCommunication(ReportRequest(message))
                    println("Sucessfully Reported!!")
                }
                "4"->{
                    println("Finished")
                    break
                }
                null ->{
                    println("다시 입력해주세요.")
                }
            }
        }
    }

    private fun clearScreen() {
        print("\u001B[H\u001B[2J")
        System.out.flush()
    }
}