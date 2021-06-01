package com.cslibrary.client.ui

import org.springframework.stereotype.Component
import java.util.*

@Component
class SecondPage (
    private val shape : Shape,
    private val seat : Seat,
){
    fun secondPage(){
        val scanner : Scanner = Scanner(System.`in`)
        val realtime = RealTimePage(shape,seat)
        shape.makeRec(3, "Select Seat")
        seat.chooseSeat(2)
        val time = 3
        realtime.realtime()
        return
    }
}