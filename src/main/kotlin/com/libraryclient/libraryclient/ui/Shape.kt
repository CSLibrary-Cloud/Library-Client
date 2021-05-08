package com.libraryclient.libraryclient.ui

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
}