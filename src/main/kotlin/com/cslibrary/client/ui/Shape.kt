package com.cslibrary.client.ui

import com.cslibrary.client.data.response.SeatResponse
import org.springframework.stereotype.Component

@Component
class Shape {

    fun makeRec(num : Int, str : String){

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

        for (i in arr.indices){
            //NxN 에서 N 만큼 나누기
            if(arr[i].isUsing) isUs = "T"
            else isUs = "F"

            if(arr[i].seatNumber<9)
                print("${arr[i].seatNumber+1}  > $isUs | ")
            else
                print("${arr[i].seatNumber+1} > $isUs | ")

            if(i>0 && (i+1)%5==0)print("\n")
        }
        print("\n")
    }
}