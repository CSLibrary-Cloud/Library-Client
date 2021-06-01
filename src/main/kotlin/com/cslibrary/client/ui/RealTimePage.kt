package com.cslibrary.client.ui

import java.util.*
import kotlin.concurrent.timer


class RealTimePage (
    private val shape : Shape,
    private val seat : Seat
    ) {

    val scanner: Scanner = Scanner(System.`in`)
    val thirdpage = ThirdPage(shape, seat)
    var cnt = 0
    var flag: Boolean = true
    lateinit var usertimer: Timer

    fun realtime() : Int{

        usertimer = timer(period = 1000,initialDelay = 1000) {
            clearScreen()
            shape.makeTime(cnt)

            if (cnt % 5 == 0) {
                //5초마다 갱신
                shape.makeLeaderBoard(1)
            } else {
                shape.makeLeaderBoard(0)
            }

            cnt++

        }
        if (scanner.hasNext()) {
            usertimer.cancel()
            usertimer.purge()
            clearScreen()
            scanner.nextLine()


            while(true){
                shape.makeRec(3,"Pause Page")
                println("1. 다시 시작\n2.나가기")
                print("Choose the number : ")
                val ch = scanner.next()
                scanner.nextLine()

                if (ch == "1") {
                    print("다시 시작 선택")
                    break

                } else if (ch == "2") {
                    print("나가기 선택")
                    val num = thirdpage.thirdPage()

                    if (num == 5) {
                        break
                    }
                    else if (num == 6) {
                        usertimer.cancel()
                        flag = false
                        break
                    }
                }
                else {
                    println("입력이 잘못되었습니다. 다시 입력해주세요.")
                }
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