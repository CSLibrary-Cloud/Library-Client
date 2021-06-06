package com.cslibrary.client.ui

import com.cslibrary.client.data.request.SeatSelectRequest
import com.cslibrary.client.data.response.SaveLeftTimeResponse
import com.cslibrary.client.data.response.SeatResponse
import com.cslibrary.client.data.response.SeatSelectResponse
import com.cslibrary.client.data.response.UserLeftTimeResponse
import com.cslibrary.client.server.ServerManagement
import org.springframework.stereotype.Component
import java.util.*

@Component
class Seat (
    private val shape: Shape,
    private val serverManagement: ServerManagement
) {

    val scanner: Scanner = Scanner(System.`in`)

    private fun showSeatAsUI(): Boolean {
        val seatResponse : List<SeatResponse>? = serverManagement.getSeatInformation()

        if(seatResponse == null){
            println("Get Seat Information Failed!\nGoing back to Main Page")
            println("\nPress enter key to continue..")
            scanner.nextLine()
            return false
        }

        return if(seatResponse.isNotEmpty()){
            shape.makeSeat(seatResponse)
            true
        } else {
            println("Get Seat Information Failed!\nGoing back to Main Page")
            println("\nPress enter key to continue..")
            scanner.nextLine()
            return false
        }
    }

    fun changeSeat() {
        val inputNumber: Int = chooseSeatInput() ?: run {
            println("null Input\nFailed to change seat!")
            println("\nPress enter key to continue..")
            scanner.nextLine()
            return
        }
        serverManagement.seatChangeCommunication(SeatSelectRequest(inputNumber-1))
    }

    fun reserveSeat(): UserLeftTimeResponse? {
        val inputNumber: Int = chooseSeatInput() ?: run {
            print("Fail to select Seat!\nGoing back to Main Page")
            println("\nPress enter key to continue..")
            scanner.nextLine()
            return null
        }

        return serverManagement.seatSelectCommunication(SeatSelectRequest(inputNumber-1))
    }


    // isSeatSelect == true -> 좌석 선택, false이면 좌석 수정
    private fun chooseSeatInput(): Int? {
        if (!showSeatAsUI()) return null

        print("Choose the Seat Number : ")
        val num: String = scanner.nextLine() ?: return null
        return convertStringToInt(num) ?: return null
    }

    private fun convertStringToInt(target: String): Int? {
        return runCatching {
            target.toInt()
        }.getOrNull()
    }
}