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
        while(true){
            shape.makeRec(3, "Second Page")
            println("1. Show Seat\n2. Select Seat\n3. Change User State\n4.Report User\n5.Extend Time\n6.Main Page")
            print("Choose the number : ")
            val ch = scanner.next()
            when(ch!!) {
                "1" -> {
                    println("Show Seat Selected")
                    seat.showSeat()
                }
                "2"->{
                    println("Select Seat Selected")
                    seat.chooseSeat()
                }
                //3,4
                "6"->{
                    println("Return to Main Page")
                    return
                }
                null ->{
                    println("다시 입력해주세요.")
                }
            }
        }
    }
}