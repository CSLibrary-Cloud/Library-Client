package com.cslibrary.client.ui

import com.cslibrary.client.data.response.SeatResponse
import com.cslibrary.client.server.ServerManagement
import java.util.*

class Seat (
    private val shape: Shape,
    private val serverManagement: ServerManagement
) {

    val scanner: Scanner = Scanner(System.`in`)

    private fun showSeat() {

        //seat 확인

        val arr = listOf<SeatResponse>()

        // SeatResponse arr 에 담기

        shape.makeSeat(arr)
    }

    fun chooseSeat() {

        showSeat()

        while (true) {
            print("Choose the Seat Number : ")
            val num = scanner.next()
            println(num)
            num?.let {
                //선택 좌석 서버 전달

            }
            if (num == null) {
                print("좌석 입력을 하지 않았습니다. 다시 입력해주세요.")
            }
        }
    }
}