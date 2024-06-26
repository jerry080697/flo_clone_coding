package com.example.flo.ui.main.home

import android.os.*
import android.view.*
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomePannel2Binding

class HomePannel2Fragment:Fragment() {
    lateinit var binding: FragmentHomePannel2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomePannel2Binding.inflate(inflater,container,false)
        return binding.root
    }
}