package com.example.expandableanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expandableanimation.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        Log.i("recyclerView", "start")
        with(binding) {
            val myAdapter = MyRecyclerAdapter()

            val myList: ArrayList<MyData> = ArrayList()
            myList.add(MyData("First"))
            myList.add(MyData("Second"))
            myList.add(MyData("Third"))
            myList.add(MyData("Fourth"))
            myList.add(MyData("Fifth"))

            rv.adapter = myAdapter
            rv.layoutManager = LinearLayoutManager(applicationContext)
            //rv.addItemDecoration()
            myAdapter.myList = myList
            myAdapter.notifyDataSetChanged()
        }
    }
}