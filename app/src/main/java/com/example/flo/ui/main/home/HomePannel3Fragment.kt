package com.example.flo.ui.main.home

import android.os.*
import android.view.*
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomePannel3Binding

class HomePannel3Fragment:Fragment() {
    lateinit var binding: FragmentHomePannel3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomePannel3Binding.inflate(inflater,container,false)
        return binding.root
    }
}