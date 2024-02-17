package com.example.todaysnotice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.todaysnotice.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.ServerSocket
import java.net.Socket
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = OkHttpClient()
        var serverHost = ""

        binding.etServerHost.addTextChangedListener {
            serverHost = it.toString()
        }

        binding.btnConfirm.setOnClickListener {
            val request: Request = Request.Builder()
                .url("http://10.0.2.2:8080")
                // .url("http://${serverHost}:8080")
                .build()

            val callback = object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Client", e.toString())

                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "수신에 실패했습니다", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val response = response.body?.string()

                        val message = Gson().fromJson(response, Message::class.java) // parsing

                        runOnUiThread {
                            binding.tvInformation.visibility = View.VISIBLE
                            binding.tvInformation.text = message.mes

                            binding.etServerHost.isVisible = false
                            binding.btnConfirm.isVisible = false
                        }

                    } else {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            client.newCall(request).enqueue(callback)


            /*Thread {
                try {
                    val socket = Socket("10.0.2.2", 8080) // 애뮬레이터에서 실행
                    val printer = PrintWriter(socket.getOutputStream())
                    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

                    // 쓰기
                    printer.println("GET / HTTP/1.1")
                    printer.println("Host: 127.0.0.1:8080")
                    printer.println("User-Agent: android")
                    printer.println("\r\n")
                    printer.flush()

                    // client가 읽기
                    var input: String? = "-1"
                    while (input != null) input = reader.readLine()

                    reader.close()
                    printer.close()
                    socket.close()

                } catch (e: Exception) {
                    Log.e("Client", e.toString())
                }
            }.start()*/
        }

    }
}