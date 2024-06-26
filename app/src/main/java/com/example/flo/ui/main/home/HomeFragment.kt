package com.example.flo.ui.main.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.R
import com.example.flo.ui.main.album.song.SongDatabase
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.util.Timer
import com.example.flo.data.entities.Album
import com.example.flo.data.remote.CommunicationInterface
import com.example.flo.ui.main.MainActivity
import com.example.flo.ui.main.album.AlbumFragment
import com.example.flo.ui.main.album.AlbumRVAdapter
import kotlin.concurrent.scheduleAtFixedRate

class HomeFragment : Fragment(), CommunicationInterface {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()
    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var songDB: SongDatabase
    override fun sendData(album: Album) {
        if(activity is MainActivity){
            val activity=activity as MainActivity
            activity.updateMainPlayerCl(album.id)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }

            override fun onPlayAlbum(album: Album) {
                sendData(album)
            }
        })

        val bannerAdapter= BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter=bannerAdapter
        binding.homeBannerVp.orientation=ViewPager2.ORIENTATION_HORIZONTAL

        val homeAdapter= HomeVPAdapter(this)
        binding.homePannelBackgroundVp.adapter=homeAdapter
        binding.homePannelBackgroundVp.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        binding.homePannelBackgroundCi.setViewPager(binding.homePannelBackgroundVp)

        startAutoSlide(homeAdapter)
        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }

    private fun startAutoSlide(adapter: HomeVPAdapter){
        timer.scheduleAtFixedRate(3000, 3000) {
            handler.post {
                val nextItem = binding.homePannelBackgroundVp.currentItem + 1
                if (nextItem < adapter.itemCount) {
                    binding.homePannelBackgroundVp.currentItem = nextItem
                } else {
                    binding.homePannelBackgroundVp.currentItem = 0
                }
            }
        }
    }

}