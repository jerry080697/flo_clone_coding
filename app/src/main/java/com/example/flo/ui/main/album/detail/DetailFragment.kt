package com.example.flo.ui.main.album.detail

import android.os.*
import android.view.*
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentDetailBinding

class DetailFragment:Fragment() {
    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }
}