package com.example.flo.ui.main.home

import android.os.*
import android.view.*
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomePannel1Binding

class HomePannel1Fragment:Fragment() {
    lateinit var binding: FragmentHomePannel1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomePannel1Binding.inflate(inflater,container,false)
        return binding.root
    }
}