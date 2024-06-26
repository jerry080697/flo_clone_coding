package com.example.flo.ui.main.album.song

import android.os.*
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentSongBinding

class SongFragment:Fragment() {
    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSongBinding.inflate(inflater,container,false)

        binding.albumToggleOffIb.setOnClickListener {
            setToggleStatus(false)
        }
        binding.albumToggleOnIb.setOnClickListener {
            setToggleStatus(true)
        }
//        binding.songLilacLayout.setOnClickListener {
//            Toast.makeText(activity,"LILAC", Toast.LENGTH_SHORT).show()
//        }
//        binding.songFluLayout.setOnClickListener {
//            Toast.makeText(activity,"Flu", Toast.LENGTH_SHORT).show()
//        }
//        binding.songCoinLayout.setOnClickListener {
//            Toast.makeText(activity,"Coin", Toast.LENGTH_SHORT).show()
//        }
//        binding.songSpringHelloLayout.setOnClickListener {
//            Toast.makeText(activity,"봄 안녕", Toast.LENGTH_SHORT).show()
//        }
        return binding.root
    }

    private fun setToggleStatus(isPlaying:Boolean) {
        if(isPlaying){
            binding.albumToggleOffIb.visibility= View.VISIBLE
            binding.albumToggleOnIb.visibility=View.GONE
        }
        else{
            binding.albumToggleOffIb.visibility= View.GONE
            binding.albumToggleOnIb.visibility=View.VISIBLE

        }
    }
}