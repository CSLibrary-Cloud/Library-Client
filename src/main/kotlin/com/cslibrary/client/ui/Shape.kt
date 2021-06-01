package com.cslibrary.client.ui

import com.cslibrary.client.data.response.SaveLeftTimeResponse
import com.cslibrary.client.data.response.SeatResponse
import org.springframework.stereotype.Component

@Component
class Shape {

    fun makeRec(num : Int, str : String){

        print("\n")
        for (i in 0..num*2){
            print("-- ")
        }

        print("\n$str\n")
        for (i in 0..num*2){
            print("-- ")
        }
        print("\n")
    }

    fun makeSeat(arr: List<SeatResponse>){
        var isUs : String = ""

        makeRec(2,"SEAT")
        println("O for Occupied, E for Empty.")

        for (i in arr.indices){
            //NxN 에서 N 만큼 나누기
            if(arr[i].isUsing) isUs = "O"
            else isUs = "E"

            if(arr[i].seatNumber<9)
                print("${arr[i].seatNumber+1}  > $isUs | ")
            else
                print("${arr[i].seatNumber+1} > $isUs | ")

            if(i>0 && (i+1)%5==0)print("\n")
        }
        print("\n")
    }

    fun makeTime(t : Long){
        //남은 시간 가져오기
        println("\n\nLeft Time : $t\n")
    }

    fun makeLeaderBoard(invokeUpdate: (()-> SaveLeftTimeResponse)){
        val saveLeftTimeResponse: SaveLeftTimeResponse = invokeUpdate.invoke()
        println("---------------------------\n")
        //리더보드 출력
        saveLeftTimeResponse.leaderBoardList.forEach {
            println(
                """
                    |Rank: ${it.rank}
                    |User Name: ${it.userName}
                    |Study Time: ${it.totalStudyTime}\n
                """.trimMargin()
            )
        }
        println("--------------------------\n")
        saveLeftTimeResponse.userNotificationList.forEach {
            println(
                """
                    |Notification: ${it.notificationTitle}
                    |Message: ${it.notificationMessage}
                """.trimMargin()
            )
        }
    }

}