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
        shape.makeRec(3, "Select Seat")
        val userLeftTimeResponse: UserLeftTimeResponse = seat.reserveSeat() ?: run {
            MainIO.printError("Cannot reserve seat!")
            return
        }
        MainIO.printNormal("Seat Reserved: ${userLeftTimeResponse.reservedSeat.reservedSeatNumber+1}")

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