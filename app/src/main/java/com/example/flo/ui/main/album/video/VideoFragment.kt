package com.example.flo.ui.main.album.video

import android.os.*
import android.view.*
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentVideoBinding

class VideoFragment:Fragment() {
    lateinit var binding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentVideoBinding.inflate(inflater,container,false)
        return binding.root
    }
}