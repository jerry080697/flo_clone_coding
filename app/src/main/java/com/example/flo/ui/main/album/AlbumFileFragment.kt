package com.example.flo.ui.main.album

import android.os.*
import android.view.*
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerAlbumFileBinding

class AlbumFileFragment:Fragment() {
    lateinit var binding: FragmentLockerAlbumFileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLockerAlbumFileBinding.inflate(inflater,container,false)
        return binding.root
    }
}