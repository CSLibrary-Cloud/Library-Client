package com.cslibrary.client.ui

import com.cslibrary.client.data.request.SaveLeftTime
import com.cslibrary.client.data.request.StateChangeRequest
import com.cslibrary.client.data.response.ExtendTimeResponse
import com.cslibrary.client.data.response.SaveLeftTimeResponse
import com.cslibrary.client.io.MainIO
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
    var cnt = 0
    var flag: Boolean = true
    lateinit var usertimer: Timer
    lateinit var breakTimer: Timer
    private var leftTimeGlobal: Long = -1
    private var breakTime: Long = 0

    fun realtime(leftTime: Long) : Int {
        if (leftTimeGlobal == -1L) {
            leftTimeGlobal = leftTime
        }

        usertimer = startClientTimer()

        if (MainIO.scanner.hasNextLine()) {
            usertimer.cancel()
            usertimer.purge()
            MainIO.clearScreen()

            // scanner 버퍼 비워주기!!
            MainIO.scanner.nextLine()

            //유저 상태 갱신
            serverManagement.stateCommunication(StateChangeRequest("break"))

            //break 상태에서 1시간이상이면 exit -> mainPage
            breakTimer = startBreakTimer()

            while(true){
                MainIO.clearScreen()
                shape.makeRec(3,"Pause Page")
                MainIO.printNormal(
                    """
                    |1. 계속 공부
                    |2. 좌석 변경
                    |3. 시간 연장[남은 시간 1시간 이상일때만!]
                    |4. 메인으로 돌아가기[종료]
                """.trimMargin()
                )

                when (MainIO.getInputNormal("Choose the number: ")) {
                    "1" -> {
                        MainIO.printNormal("계속 공부 선택")
                        serverManagement.stateCommunication(
                            StateChangeRequest("start")
                        )
                        cleanupAllTimers()
                        return 1
                    }
                    "2" -> {
                        MainIO.printNormal("Change Seat Selected")
                        seat.changeSeat()
                        cleanupAllTimers()
                        return 1
                    }
                    "3" -> {
                        if (leftTimeGlobal <= 60 * 60) {
                            MainIO.printError("남은 시간이 1시간 이하입니다. 시간을 연장할 수 없습니다.")
                        } else {
                            val extendTimeResponse: ExtendTimeResponse = serverManagement.extendTime(leftTimeGlobal) ?: run {
                                MainIO.printError("시간을 늘릴 수 없습니다. 다음에 다시 시도해 주세요.")
                                return 1
                            }
                            leftTimeGlobal = extendTimeResponse.updatedLeftTime
                            MainIO.printNormal("1시간 연장되었습니다.")
                        }
                        MainIO.waitFor()
                        cleanupAllTimers()
                        return 1
                    }
                    "4" -> {
                        cleanupAllTimers()
                        serverManagement.stateCommunication(
                            StateChangeRequest("exit")
                        )
                    }
                    else -> {
                        MainIO.printError("잘못된 입력입니다.")
                        continue
                    }
                }
                break
            }
        }
        return 2
    }

    // Start Client Timer
    private fun startClientTimer(): Timer {
        return timer(period = 1000,initialDelay = 1000) {
            MainIO.clearScreen()

            shape.makeTime(leftTimeGlobal)

            if (cnt % 5 == 0) {
                makeLeaderboardRefresh(this)
            } else {
                shape.makeLeaderBoard(null)
            }

            cnt++
            leftTimeGlobal--
            if (leftTimeGlobal <= 0L) {
                // Request Server
                handleClientTimeout(this)
            }
        }
    }

    // Start Pause[Break] Timer
    private fun startBreakTimer(): Timer {
        return timer(period = 60000,initialDelay = 1000) {
            breakTime++
            handleClientBreakTimeout(this, breakTime)
        }
    }

    // Refresh Leaderboard from server
    private fun makeLeaderboardRefresh(timerTask: TimerTask) {
        val response: SaveLeftTimeResponse = serverManagement.saveLeftTimeCommunication(SaveLeftTime(leftTimeGlobal)) ?: run {
            MainIO.printError("LeaderBoard Failed.\nGoing back to Main Page")
            MainIO.waitFor()

            flag = false
            serverManagement.stateCommunication(
                StateChangeRequest(
                    "exit"
                )
            )
            timerTask.cancel()

            throw IllegalStateException("Leaderboard communication failed!")
        }

        // Make Leader Boar with SaveLeftTimeResponse
        shape.makeLeaderBoard { response }
    }

    // When client's time is over.
    private fun handleClientTimeout(timerTask: TimerTask) {
        serverManagement.stateCommunication(StateChangeRequest("exit"))
        MainIO.printNormal("Your time has expired!!")
        MainIO.waitFor()
        timerTask.cancel()
    }

    // When Client's break time is over.
    private fun handleClientBreakTimeout(timerTask: TimerTask, breakTime: Long) {
        if (breakTime >= 60) {
            MainIO.printNormal("Break time expired. Select any menu to return to main menu.")
            serverManagement.stateCommunication(StateChangeRequest("exit"))
            timerTask.cancel()
            throw RuntimeException("Break time expired!")
        }
    }

    private fun cleanupAllTimers() {
        usertimer.cancel()
        usertimer.purge()
        breakTimer.cancel()
        breakTimer.purge()
    }
}