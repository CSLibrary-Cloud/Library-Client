package com.cslibrary.client.ui

import com.cslibrary.client.data.response.SeatResponse
import com.cslibrary.client.server.ServerManagement
import org.springframework.stereotype.Component
import java.util.*

@Component
class Seat (
    private val shape: Shape,
    private val serverManagement: ServerManagement
) {

    val scanner: Scanner = Scanner(System.`in`)

    fun showSeat() {
        val seatResponse : List<SeatResponse>? = serverManagement.getSeatInformation()
        if(seatResponse?.isNotEmpty() == true){
            if (seatResponse != null) {
                shape.makeSeat(seatResponse)
            }
        }

        print("Press Enter to go back")
        scanner.nextLine()
    }

    fun chooseSeat(n : Int) {
        showSeat()
        while (true) {
            print("Choose the Seat Number : ")
            val num = scanner.next()
            println("Choosed $num")
            num?.let {
                //선택 좌석 서버 전달

                if(n==2){
                    //좌석 선택
                }
                else if(n==3){
                    //좌석 수정
                }
            }
            if (num == null) {
                print("좌석 입력을 하지 않았습니다. 다시 입력해주세요.")
            }
        }
    }
}