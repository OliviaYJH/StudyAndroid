package com.example.webtoon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.webtoon.databinding.FragmentSecondBinding

class BFragment(): Fragment() {
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater)
        return binding.root
    }
}