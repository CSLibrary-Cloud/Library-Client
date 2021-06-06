package com.cslibrary.client.ui

import com.cslibrary.client.data.response.UserLeftTimeResponse
import com.cslibrary.client.io.MainIO
import org.springframework.stereotype.Component

@Component
class SecondPage (
    private val shape : Shape,
    private val seat : Seat,
    private val realTimePage: RealTimePage
){
    fun secondPage(){
        MainIO.clearScreen()
        shape.makeRec(3, "좌석 선택 메뉴")
        val userLeftTimeResponse: UserLeftTimeResponse = seat.reserveSeat() ?: run {
            MainIO.printError("자석을 예약할 수 없습니다!")
            return
        }
        MainIO.printNormal("${userLeftTimeResponse.reservedSeat.reservedSeatNumber+1} 번 좌석이 예약되었습니다.")

        var num : Int = 1
        while(true){
            num = runCatching {
                realTimePage.realtime(userLeftTimeResponse.leftTime)
            }.getOrElse {
                return
            }
            if(num == 2) break
        }
    }
}