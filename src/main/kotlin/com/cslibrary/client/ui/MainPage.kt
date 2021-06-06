package com.cslibrary.client.ui

import com.cslibrary.client.data.request.ReportRequest
import com.cslibrary.client.io.MainIO
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
            MainIO.initIO()
            mainPage()
        }
    }

    private fun mainPage() {
        while(true){
            MainIO.clearScreen()
            shape.makeRec(5,"Library System")
            shape.makeRec(3, "Main Page")
            MainIO.printNormal(
                """
                    |1. Login
                    |2. Register
                    |3. Report User
                    |4. Finish
                """.trimMargin()
            )
            when(MainIO.getInputNormal("Choose the number : ")) {
                "1" -> {
                    MainIO.printNormal("Login Selected")
                    login.loginUser()
                }
                "2"->{
                    MainIO.printNormal("Register Selected")
                    register.registerUser()
                }
                "3"->{
                    shape.makeRec(3,"Report User")
                    val title: String = MainIO.getInputNormal("| Message Title: ")
                    val message: String = MainIO.getInputNormal("| Message: ")
                    serverManagement.reportCommunication(ReportRequest(message))
                    MainIO.printNormal("Successfully Reported!!")
                }
                "4"->{
                    MainIO.printNormal("Finished")
                    break
                }
                else ->{
                    MainIO.printNormal("다시 입력해주세요.")
                }
            }
        }
    }
}