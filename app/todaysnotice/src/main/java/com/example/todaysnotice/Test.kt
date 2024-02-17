package com.example.todaysnotice

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main() {
    // JVM에서 돌아감

    // network는 main Thread에서 작동할 수 없도록 되어 있음 -> Thread 이용해 새로운 스레드 생성
    Thread {
        val port = 8080
        val server = ServerSocket(port)

        while (true) {
            val socket = server.accept()
            //socket.getInputStream() // client로부터 들어오는 stream = client의 socket.outputStream
            //socket.getOutputStream() // client에게 데이터를 주는 stream = client의 socket.inputStream

            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val printer = PrintWriter(socket.getOutputStream())

            var input: String? = "-1"
            while (input != null && input != "") {
                input = reader.readLine() // 데이터 읽어옴
            }

            println("READ DATA $input")

            printer.println("HTTP/1.1 200 OK") // 규격 부분 - 정상 데이터 수신 및 정상 데이터 전송할 것임을 알림
            printer.println("Content-Type: text/html\r\n") // Header 부분
            // Body 부분
            printer.println("{\"message\":\"Hello World\"}")
            printer.println("\r\n")
            printer.flush() // 잔여 데이터 배출

            printer.close() // 끊음
            reader.close()
            socket.close()
        }

    }.start()
}