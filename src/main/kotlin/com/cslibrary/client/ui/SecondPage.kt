package com.cslibrary.client.ui

import com.cslibrary.client.data.response.UserLeftTimeResponse
import org.springframework.stereotype.Component

@Component
class SecondPage (
    private val shape : Shape,
    private val seat : Seat,
    private val realTimePage: RealTimePage
){
    fun secondPage(){
        clearScreen()
        shape.makeRec(3, "Select Seat")
        val userLeftTimeResponse: UserLeftTimeResponse = seat.reserveSeat() ?: run{
            return
        }
        println("Seat Reserved: ${userLeftTimeResponse.reservedSeat.reservedSeatNumber+1}")

        var num : Int = 1
        while(true){
            num = realTimePage.realtime(userLeftTimeResponse.leftTime)
            if(num == 2) break
        }
    }

    private fun clearScreen() {
        print("\u001B[H\u001B[2J")
        System.out.flush()
    }
}