package com.cslibrary.client.ui
import java.util.*

class ThirdPage(
    private val shape : Shape,
    private val seat : Seat,
) {

    fun thirdPage() : Int{
        val sc : Scanner = Scanner(System.`in`)
        while(true){
            shape.makeRec(3, "Third Page")
            println("1. Show Seat\n2. Change Seat\n3.Report User\n4.Extend Time\n5.Timer & LeaderBoard\n6.Main Page")
            print("\nChoose the number : ")
            when(sc.next()) {
                "1" -> {
                    println("Show Seat Selected")
                    seat.showSeat()
                }
                "2"->{
                    println("Change Seat Selected")
                    seat.chooseSeat(3)
                }
                //3= 신고, 4= 시간연장
                "5"->{
                    println("Timer & LeaderBoard Selected")
                    return 5
                }
                "6"->{
                    println("Return to Main Page")
                    return 6
                }
                null ->{
                    println("다시 입력해주세요.")
                }
            }
        }
    }
}