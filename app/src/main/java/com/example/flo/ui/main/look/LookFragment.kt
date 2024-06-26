package com.example.flo.ui.main.look

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.ui.main.album.song.SongDatabase
import com.example.flo.ui.main.album.song.SongRVAdapter
import com.example.flo.data.remote.FloChartResult
import com.example.flo.data.remote.SongService
import com.example.flo.databinding.FragmentLookBinding

class LookFragment : Fragment(), LookView {

    lateinit var binding: FragmentLookBinding
    private lateinit var songDB: SongDatabase
    private lateinit var floChartAdapter: SongRVAdapter

//    private lateinit var chartBtn : Button
//    private lateinit var videoBtn : Button
//    private lateinit var genreBtn : Button
//    private lateinit var situationBtn : Button
//    private lateinit var atmosphereBtn : Button
//
//    private lateinit var buttonList: List<Button>
//
//    private lateinit var chartTv : TextView
//    private lateinit var videoTv : TextView
//    private lateinit var genreTv : TextView
//    private lateinit var situationTv : TextView
//    private lateinit var atmosphereTv : TextView
//
//    private lateinit var textList: List<TextView>
//
//    lateinit var scrollView : ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!

//        // 스크롤 뷰 초기화
//        scrollView = binding.lookSv
//
//        // 버튼 초기화
//        chartBtn = binding.lookChartBtn
//        videoBtn =  binding.lookVideoBtn
//        genreBtn =  binding.lookGenreBtn
//        situationBtn =  binding.lookSituationBtn
//        atmosphereBtn =  binding.lookAtmostphereBtn
//
//        buttonList = listOf(chartBtn, videoBtn, genreBtn, situationBtn, atmosphereBtn)
//
//        // 텍스트 초기화
//        chartTv = binding.lookChartTv
//        videoTv = binding.lookVideoTv
//        genreTv = binding.lookGenreTv
//        situationTv = binding.lookSituationTv
//        atmosphereTv = binding.lookAtmostphereTv

        //textList = listOf(chartTv, videoTv, genreTv, situationTv, atmosphereTv)

        //setButtonClickListeners()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //initRecyclerview()
        getSongs()
    }


    private fun getSongs(){
        val songService= SongService()
        songService.setLookView(this)

        songService.getSongs()
    }
//    private fun setButtonClickListeners() {
//        for (i in buttonList.indices) {
//            val button = buttonList[i]
//
//            button.setOnClickListener {
//                initButton(i)
//            }
//        }
//    }
//    private fun initRecyclerview(){
//        val recyclerView = binding.lookChartSongRv
//        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
//        val lookAlbumRVAdapter = SavedSongRVAdapter()
//
//        binding.lookChartSongRv.adapter = lookAlbumRVAdapter
//        lookAlbumRVAdapter.addSongs(songDB.songDao().getSongs() as ArrayList<Song>)
//    }
    private fun initRecyclerview(result: FloChartResult){
        floChartAdapter= SongRVAdapter(requireContext(),result)
        binding.lookFloChartRv.adapter=floChartAdapter
    }
//    private fun initButton(idx : Int) {
//        for(presentBtn : Button in buttonList) {
//            if(presentBtn == buttonList[idx]) {
//                presentBtn.setBackgroundResource(R.drawable.selected_button)
//            } else {
//                presentBtn.setBackgroundResource(R.drawable.not_selected_button)
//            }
//        }
//        scrollView.smoothScrollTo(0, textList[idx].top)
//    }

    override fun onGetSongLoading() {
        binding.lookLoadingPb.visibility=View.GONE
    }

    override fun onGetSongSuccess(code: Int, result: FloChartResult) {
        binding.lookLoadingPb.visibility=View.GONE
        initRecyclerview(result)
    }

    override fun onGetSongFailure(code: Int, message: String) {
        binding.lookLoadingPb.visibility=View.GONE
        Log.d("LOOK-FRAG/SONG-RESPONSE",message)
    }
}