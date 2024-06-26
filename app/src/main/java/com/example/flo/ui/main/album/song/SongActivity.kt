package com.example.flo.ui.main.album.song

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import android.os.*
import android.util.Log
import android.view.View
import com.example.flo.ui.main.home.CustomSnackbar
import com.example.flo.R
import com.example.flo.data.entities.Song
import com.google.gson.Gson

class SongActivity :AppCompatActivity(){
    //전역 변수
    lateinit var binding:ActivitySongBinding
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer?=null
    private var gson: Gson = Gson()
    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var song : Song = Song()
    var nowPos = 0


    override fun onCreate(savedInstanceState: Bundle?) { //가장 먼저 실행되는 함수
        super.onCreate(savedInstanceState)
        binding=ActivitySongBinding.inflate(layoutInflater) //xml에 표기된 레이아웃을 객체화 시킴
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListener()
    }
    override fun onPause() {
        super.onPause()
        songs[nowPos].second = (songs[nowPos].playTime * binding.songProgressSb.progress) / 100000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("songId", songs[nowPos].id)
        editor.putInt("second", songs[nowPos].second)
        editor.apply()
    }
    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() //미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null //미디어 플레이어 해제
    }

    private fun setRepeatStatus(isPlaying: Boolean){
        if(isPlaying){
            binding.songRepeatIv.visibility=View.VISIBLE //반복재생
            binding.songRepeatOffIv.visibility=View.GONE //한곡재생
        }
        else{
            binding.songRepeatIv.visibility=View.GONE
            binding.songRepeatOffIv.visibility=View.VISIBLE
        }
    }
    private fun initSong(){
        val spf=getSharedPreferences("song", MODE_PRIVATE)
        val songId=spf.getInt("songId",0)

        nowPos=getPlayingSongPosition(songId)

        Log.d("now song ID",songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos])
    }
    private fun getPlayingSongPosition(songId:Int):Int{
        for(i in 0 until songs.size){
            if(songs[i].id==songId){
                return i
            }
        }
        return 0
    }
    private fun initPlayList(){
        songDB= SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }
    private fun initClickListener(){
        binding.songDownIb.setOnClickListener {
            finish()
        }
        binding.songPlayerControllerPlayIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPlayerControllerPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songRepeatIv.setOnClickListener {
            setRepeatStatus(false)
        }
        binding.songRepeatOffIv.setOnClickListener {
            setRepeatStatus(true)
        }
        binding.songShuffleIv.setOnClickListener {
            setShuffleStatus(false)
        }
        binding.songShuffleOffIv.setOnClickListener {
            setShuffleStatus(true)
        }
        binding.songPlayerControllerNextIv.setOnClickListener {
            moveSong(+1)
        }
        binding.songPlayerControllerPreviousIv.setOnClickListener {
            moveSong(-1)
        }
        binding.songAlbumLikeOffIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
            CustomSnackbar.make(binding.root, "좋아요 한 곡에 담겼습니다").show()
        }
    }
    private fun setLike(isLike:Boolean){
        songs[nowPos].isLike=!isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if(!isLike){
            binding.songAlbumLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songAlbumLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }
    private fun moveSong(direct:Int){
        if(nowPos + direct < 0){
            CustomSnackbar.make(binding.root, "처음 곡입니다.").show()
            return
        }
        if(nowPos + direct >= songs.size){
            CustomSnackbar.make(binding.root, "마지막 곡입니다").show()
            return
        }
        nowPos+=direct

        timer.interrupt()
        resetMedia()
        startTimer()
        mediaPlayer?.release()
        mediaPlayer=null
        setPlayer(songs[nowPos])
    }
    private fun setPlayer(song: Song){
        binding.songTitleTv.text=song.title
        binding.songSingerTv.text=song.singer
        binding.songStartTimeTv.text=String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndTimeTv.text=String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 100000 / song.playTime)
        binding.songAlbumIv.setImageResource(song.coverImg!!)

        val music=resources.getIdentifier(song.music,"raw",this.packageName)
        mediaPlayer=MediaPlayer.create(this,music)

        if (mediaPlayer == null) resetMedia()
        else {
            if (song.second != 0) mediaPlayer?.seekTo(song.second * 1000 + 1200)
        }
        if(song.isLike){
            binding.songAlbumLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songAlbumLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }

        startTimer()

        setPlayerStatus(song.isPlaying)
    }
    private fun setShuffleStatus(isShuffle: Boolean){
        if(isShuffle){
            binding.songShuffleIv.visibility=View.VISIBLE
            binding.songShuffleOffIv.visibility=View.GONE
        }
        else
        {
            binding.songShuffleIv.visibility=View.GONE
            binding.songShuffleOffIv.visibility=View.VISIBLE
        }
    }
    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)
        timer.start()
    }
    inner class Timer(private val playTime:Int, var isPlaying:Boolean = true):Thread(){
        private var second : Int = songs[nowPos].second
        private var mills:Float = (songs[nowPos].second*1000).toFloat()

        override fun run() {
            super.run()
            try{
                while(true){
                    if(second>=playTime){
                        second=0
                        mills=0f
                        resetMedia()
                        if (binding.songRepeatIv.visibility == View.VISIBLE) {
                            runOnUiThread{
                                setPlayerStatus(false)

                            }
                        }
                        else{
                            runOnUiThread{
                                setPlayerStatus(true)
                            }
                        }
                    }

                    if(isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread{
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }
                        if(mills % 1000 == 0f){
                            runOnUiThread{
                                binding.songStartTimeTv.text= String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            }catch(e:InterruptedException){
                Log.d("Song","쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }
    fun setPlayerStatus(isPlaying : Boolean) {
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if (isPlaying) {
            binding.songPlayerControllerPlayIv.visibility = View.GONE
            binding.songPlayerControllerPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else {
            binding.songPlayerControllerPlayIv.visibility = View.VISIBLE
            binding.songPlayerControllerPauseIv.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
    }

    private fun resetMedia() {
        mediaPlayer?.reset()
        val music = resources.getIdentifier(songs[nowPos].music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
    }

}