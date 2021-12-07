package com.soyeb.zerohoursmedicalservice.util

class Constrants_Variable {

    companion object {
        val balance_delay_millisecond:Long = 5000
        val balance_sleep_millisecond:Long = 5000
        val balance_tk:String = ""

        //Session Timeout time setting
        // Minute * 60 * 1000
        //val logout_time:Long = SessionUtil.sessionValidMinute.toLong() * 60 * 1000
        val logout_interval:Long = 1000

        val retrofit_connection_timeout_in_second:Long = 60*2
        val retrofit_read_timeout_in_second:Long = 60*2
        val retrofit_wright_timeout_in_second:Long = 60*2

        val languageCode:String = "BAN"

        var headerUserPass = "gOttTw7/P9Dvz6we0rd3SaDG2Rurpbmd9hFrtCUDiIn1yCV2dzWXb4YNVF/KhK9XepJQlSQDxec/sD1Z6ESlcm2vLrhhOXF7U2V6cfECt2DfJXP3E87FRYg9J6Menf76qfbTXVR6qaM7kcWxkj/d1HW+QRgdC5vU8U9BEfB8yt1D28pETeL7yOQrcPOiJUvRZGZNZahxg2YC7OT07K643cKaT0OPZjs2y2XaTJJZaIO7hRcPtThtCWYv74Cu2dFauitc1S4NSIpT0TQQTrUwAC6Un2rXu+XB7GgJmIp8M4fHaCS7LPHTCoBJITsZaB+2tm2fYm3X7Z68+X8CXMuxkfPUmYWQZtDi0kPrtu0QZJxOUiJ1+QuBzGPSf++02I6ZoMyh4tnMR0xWHTwDXlC/PHhe7REpOSCOadzlJNQFN4E9T9PqOOo4XuXK9Mu5WUSuG/LvVpdAorsZXEcQ5fRrh1QRjGtGP2qQs5CB0Nta+I/qMvx9StxVMIigpmNiMs/z6Coq10qSO2pEvsfKMWDoRcrC3evbsa2X28ti/F2xrHtrZ+DddI+rpr4YV2vNWhn1iHQAAfwHs2khhNIw1udT3Yh4fShtF+CoFd4+XaifkE83mIwPMvSs5p3SlRgqhquODRbBvjaUwVgH2wh2B0DqAhMO6Od6h3asB9y7FFQfM+k="


        val select:String = "-Select Options-"

    }
}