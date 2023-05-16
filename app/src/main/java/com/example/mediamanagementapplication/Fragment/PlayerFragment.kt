package com.example.playit.Fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mediamanagementapplication.R
import com.example.mediamanagementapplication.databinding.FragmentPlayerBinding
import com.example.playit.Model.Music
import com.example.playit.Model.formatDuration

import com.example.playit.ViewModel.MusicViewModel



class PlayerFragment : Fragment() {
    private var binding: FragmentPlayerBinding?=null
    private  var mediaPlayer: MediaPlayer?=null
    private var isPlaying:Boolean = false
    private var musicViewModel:MusicViewModel?=null
    private var currentIndex:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentPlayerBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        mediaPlayer = MediaPlayer()
        createPlayer()

        //play / pause button
        binding?.btnpause?.setOnClickListener {
            if (isPlaying){
                pauseMusic()
            }
            else{
                createPlayer()
            }
        }
        // play previous song
        binding?.btnpre?.setOnClickListener {
            previousNextSong(false)
        }
        // play next song
        binding?.btnnext?.setOnClickListener {
            previousNextSong(true)
        }

        // Favourite Music
        binding?.imgbtnfav?.setOnClickListener {
            try {
              //  findNavController().navigate(R.id.favoriteFragment)
//             title=arguments?.getString("title")
//             album=arguments?.getString("album")
//             duration=arguments?.getString("duration")
//             path=arguments?.getString("path")
////                val music:Music?=null
////
////                val title=music?.title
////                val album=music?.album
////                val artist=music?.artist
////                val duration=music!!.duration
////                val path=music.path
//
//               // Toast.makeText(requireContext(), "${album}", Toast.LENGTH_SHORT).show()
//
////            musicViewModel?.insertMusic(title.toString(),album.toString(),artist.toString(),
////                duration!!.toLong(),path.toString())
//                val gg = Music(null,title.toString(),album.toString(),duration!!.toLong(),path.toString())
//                musicViewModel?.insertMusic(title.toString(),album.toString(),duration!!.toLong(),path.toString())
//               // Toast.makeText(requireContext(), "${musicViewModel}", Toast.LENGTH_SHORT).show()
//                binding?.imgbtnfav?.setImageResource(R.drawable.favorite_fill)
                //Toast.makeText(requireContext(), "${formatDuration(duration!!.toLong())}", Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        return binding?.root
    }

    fun insertfavorite(music: Music){

    }
    // create and play
    fun createPlayer(){
        try {
            val name=arguments?.getString("title")
            val duration=arguments?.getString("duration")
            val path=arguments?.getString("path")
//            val position=arguments?.getString("position")
//            Toast.makeText(requireContext(), "${position}", Toast.LENGTH_SHORT).show()
            binding?.txtsongname?.text=name
            binding?.txtsongname?.setSelected(true)
            binding?.txttimeduration?.text= formatDuration(duration!!.toLong())

            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(path)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
            isPlaying=true
            binding?.btnpause?.setIconResource(R.drawable.pause_icon)
        }catch (e:Exception){
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    // pause
    fun pauseMusic(){
        try {
            mediaPlayer?.pause()
            isPlaying=false
            binding?.btnpause?.setIconResource(R.drawable.play_icon)
        }catch (e:Exception){
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
        }

    }
    // previous next song
    fun previousNextSong(increment:Boolean){
        try {
            if (increment){
                val position=arguments?.getString("position")
                var pos:Int=position!!.toInt()
                    ++pos
                    Toast.makeText(requireContext(), "${pos}", Toast.LENGTH_SHORT).show()
                    createPlayer()

            }
            else{
                val position=arguments?.getString("position")
                var pos:Int=position!!.toInt()
                Toast.makeText(requireContext(), "${pos}", Toast.LENGTH_SHORT).show()
                --pos
                createPlayer()
            }
        }catch (e:Exception){
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

}