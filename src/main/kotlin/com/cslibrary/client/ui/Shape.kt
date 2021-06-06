package com.cslibrary.client.ui

import com.cslibrary.client.data.response.SaveLeftTimeResponse
import com.cslibrary.client.data.response.SeatResponse
import com.cslibrary.client.io.MainIO
import org.springframework.stereotype.Component

@Component
class Shape {

    private var saveLeftTimeResponse: SaveLeftTimeResponse? = null

    fun makeRec(num : Int, str : String){

        MainIO.printNormal("")
        for (i in 0..num*2){
            MainIO.printNormal("-- ", false)
        }

        MainIO.printNormal("\n$str\n", false)
        for (i in 0..num*2){
            MainIO.printNormal("-- ", false)
        }
        MainIO.printNormal("")
    }

    fun makeSeat(arr: List<SeatResponse>){
        var isUs : String = ""

        makeRec(2,"SEAT")
        MainIO.printNormal("O for Occupied, E for Empty.")

        for (i in arr.indices){
            //NxN 에서 N 만큼 나누기
            if(arr[i].isUsing) isUs = "O"
            else isUs = "E"

            if(arr[i].seatNumber<9)
                MainIO.printNormal("${arr[i].seatNumber+1}  > $isUs | ", false)
            else
                MainIO.printNormal("${arr[i].seatNumber+1} > $isUs | ", false)

            if(i>0 && (i+1)%5==0)MainIO.printNormal("")
        }
        MainIO.printNormal("")
    }

    fun makeTime(t : Long){
        //남은 시간 가져오기
        MainIO.printNormal("\n\nLeft Time : $t\n")
    }

    fun makeLeaderBoard(invokeUpdate: (()-> SaveLeftTimeResponse)? = null){
        if (invokeUpdate != null) {
            saveLeftTimeResponse = invokeUpdate.invoke()
        }
        MainIO.printNormal("---------------------------\n")
        //리더보드 출력
        saveLeftTimeResponse?.leaderBoardList?.forEach {
            MainIO.printNormal(
                """
                    |Rank: ${it.rank}
                    |User Name: ${it.userName}
                    |Study Time: ${it.totalStudyTime}
                    |
                """.trimMargin()
            )
        }
        MainIO.printNormal("--------------------------\n")
        saveLeftTimeResponse?.userNotificationList?.forEach {
            MainIO.printNormal(
                """
                    |Notification: ${it.notificationTitle}
                    |Message: ${it.notificationMessage}
                    |
                """.trimMargin()
            )
        }
    }

}