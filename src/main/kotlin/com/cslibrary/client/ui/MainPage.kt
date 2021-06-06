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
            shape.makeRec(3, "메인 화면")
            MainIO.printNormal(
                """
                    |1. 로그인
                    |2. 회원가입
                    |3. 신고
                    |4. 종료
                """.trimMargin()
            )
            when(MainIO.getInputNormal("메뉴 숫자를 선택하세요: ")) {
                "1" -> {
                    MainIO.printNormal("로그인이 선택되었습니다.")
                    login.loginUser()
                }
                "2"->{
                    MainIO.printNormal("회원가입이 선택되었습니다.")
                    register.registerUser()
                }
                "3"->{
                    shape.makeRec(3,"신고")
                    val message: String = MainIO.getInputNormal("| 신고 내용: ")
                    serverManagement.reportCommunication(ReportRequest(message))
                    MainIO.printNormal("성공적으로 신고가 완료되었습니다!!!")
                }
                "4"->{
                    MainIO.printNormal("이용해 주셔서 감사합니다.")
                    break
                }
                else ->{
                    MainIO.printNormal("다시 입력해주세요.")
                }
            }
        }
    }
}