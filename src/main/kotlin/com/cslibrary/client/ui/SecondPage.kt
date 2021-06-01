package com.cslibrary.client.ui

import org.springframework.stereotype.Component

@Component
class SecondPage (
    private val shape : Shape,
    private val seat : Seat,
){
    fun secondPage(){
        val realtime = RealTimePage(shape,seat)
        shape.makeRec(3, "Select Seat")
        seat.chooseSeat(2)
        val time = 3

        var num : Int = 1
        while(true){
            num = realtime.realtime()
            if(num == 2) break
        }
    }
}