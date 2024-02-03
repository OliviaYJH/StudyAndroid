package com.example.expandableanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import com.example.expandableanimation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun expand(view: View) {
        val v = if (binding.tvDetails.visibility == View.GONE) View.VISIBLE else View.GONE
        TransitionManager.beginDelayedTransition(binding.linearLayout, AutoTransition())
        binding.tvDetails.visibility = v
    }
}