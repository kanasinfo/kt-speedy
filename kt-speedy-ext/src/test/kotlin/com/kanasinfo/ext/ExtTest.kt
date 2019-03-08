package com.kanasinfo.ext

import org.joda.time.DateTime
import org.junit.Test

class ExtTest{

    @Test
    fun testDay(){
        println(DateTime.now().startOfDay())
        println(DateTime.now().endOfDay())
        println(DateTime.now().startOfMonth())
        println(DateTime.now().endOfMonth())
    }


}