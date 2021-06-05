package com.cslibrary.client.ui

import com.cslibrary.client.data.request.SaveLeftTime
import com.cslibrary.client.data.request.StateChangeRequest
import com.cslibrary.client.server.ServerManagement
import kotlinx.coroutines.*
import org.springframework.stereotype.Component
import java.util.*
import kotlin.concurrent.timer


@Component
class RealTimePage (
    private val shape : Shape,
    private val serverManagement: ServerManagement,
    private val seat: Seat
) {

    val scanner: Scanner = Scanner(System.`in`)
    var cnt = 0
    var flag: Boolean = true
    lateinit var usertimer: Timer
    private var leftTimeGlobal: Long = -1

    fun realtime(leftTime: Long) : Int {
        if (leftTimeGlobal == -1L) {
            leftTimeGlobal = leftTime
        }

        usertimer = timer(period = 1000,initialDelay = 1000) {
            clearScreen()

            shape.makeTime(leftTimeGlobal)

            if (cnt % 5 == 0) {
                //5초마다 갱신

                val response = serverManagement.saveLeftTimeCommunication(SaveLeftTime(leftTimeGlobal))

                if(response==null){
                    println("LeaderBoard Failed.\nGoing back to Main Page")
                    println("\nPress enter key to continue..")
                    flag = false
                    serverManagement.stateCommunication(
                        StateChangeRequest(
                            "exit"
                        )
                    )
                    cancel()
                }

                shape.makeLeaderBoard {
                    serverManagement.saveLeftTimeCommunication(
                        SaveLeftTime(leftTimeGlobal)
                    )!!
                }
            } else {
                shape.makeLeaderBoard(null)
            }
            cnt++
            leftTimeGlobal--
            if (leftTimeGlobal <= 0L) {
                println("Your time has expired!!")
                println("Press enter key to continue..")
                flag = false
                serverManagement.stateCommunication(
                    StateChangeRequest(
                        "exit"
                    )
                )
                cancel()
            }
        }

        if (scanner.hasNextLine()) {
            if (!flag) return 2
            usertimer.cancel()
            usertimer.purge()
            clearScreen()

            // scanner 버퍼 비워주기!!
            scanner.nextLine()

            //유저 상태 갱신
            serverManagement.stateCommunication(
                StateChangeRequest("break")
            )

            //break 상태에서 1시간이상이면 exit -> mainPage
            var breakTime: Long = 0
            val breakTimer: Timer = timer(period = 60000,initialDelay = 1000) {
                breakTime++
                if (breakTime >= 60) {
                    println("Break time expired. Select any menu to return to main menu.")
                    serverManagement.stateCommunication(
                        StateChangeRequest(
                            "exit"
                        )
                    )
                    flag = false
                    cancel()
                }
            }

            while(true){
                clearScreen()
                shape.makeRec(3,"Pause Page")
                println("""
                    |1. 계속 공부
                    |2. 좌석 변경
                    |3. 시간 연장[남은 시간 1시간 이상일때만!]
                    |4. 메인으로 돌아가기[종료]
                """.trimMargin())
                print("Choose the number : ")
                val ch = scanner.nextLine()

                if (!flag) return 2

                when (ch) {
                    "1" -> {
                        println("계속 공부 선택")
                        serverManagement.stateCommunication(
                            StateChangeRequest("start")
                        )
                    }
                    "2" -> {
                        println("Change Seat Selected")
                        seat.changeSeat()
                    }
                    "3" -> {
                        if (leftTimeGlobal <= 60 * 60) {
                            println("남은 시간이 1시간 이하입니다. 시간을 연장할 수 없습니다.")
                        } else {
                            leftTimeGlobal += 60 * 60
                            println("1시간 연장되었습니다.")
                        }
                    }
                    "4" -> {
                        usertimer.cancel()
                        flag = false
                        serverManagement.stateCommunication(
                            StateChangeRequest("exit")
                        )
                    }
                    else -> {
                        println("잘못된 입력입니다.")
                        continue
                    }
                }
                break
            }
        }
        if(flag)return 1
        return 2
    }

    private fun clearScreen() {
        print("\u001B[H\u001B[2J")
        System.out.flush()
    }
}