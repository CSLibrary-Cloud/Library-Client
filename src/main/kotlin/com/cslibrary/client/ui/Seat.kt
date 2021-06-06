package com.cslibrary.client.ui

import com.cslibrary.client.data.request.SeatSelectRequest
import com.cslibrary.client.data.response.SaveLeftTimeResponse
import com.cslibrary.client.data.response.SeatResponse
import com.cslibrary.client.data.response.SeatSelectResponse
import com.cslibrary.client.data.response.UserLeftTimeResponse
import com.cslibrary.client.io.MainIO
import com.cslibrary.client.server.ServerManagement
import org.springframework.stereotype.Component
import java.util.*

@Component
class Seat (
    private val shape: Shape,
    private val serverManagement: ServerManagement
) {

    private fun showSeatAsUI(): Boolean {
        val seatResponse : List<SeatResponse> = serverManagement.getSeatInformation() ?: run {
            MainIO.printError("Get Seat Information Failed!\nGoing back to Main Page")
            MainIO.waitFor()
            return false
        }

        return if(seatResponse.isNotEmpty()){
            shape.makeSeat(seatResponse)
            true
        } else {
            MainIO.printError("Get Seat Information Failed!\nGoing back to Main Page")
            MainIO.waitFor()
            return false
        }
    }

    fun changeSeat() {
        val inputNumber: Int = chooseSeatInput() ?: run {
            MainIO.printError("null Input\nFailed to change seat!")
            MainIO.waitFor()
            return
        }
        serverManagement.seatChangeCommunication(SeatSelectRequest(inputNumber-1))
    }

    fun reserveSeat(): UserLeftTimeResponse? {
        val inputNumber: Int = chooseSeatInput() ?: run {
            MainIO.printError("Fail to select Seat!\nGoing back to Main Page")
            MainIO.waitFor()
            return null
        }

        return serverManagement.seatSelectCommunication(SeatSelectRequest(inputNumber-1))
    }


    // isSeatSelect == true -> 좌석 선택, false이면 좌석 수정
    private fun chooseSeatInput(): Int? {
        if (!showSeatAsUI()) return null
        return convertStringToInt(MainIO.getInputNormal("Choose the Seat Number: "))
    }

    private fun convertStringToInt(target: String): Int? {
        return runCatching {
            target.toInt()
        }.getOrNull()
    }
}