package com.example.playit.Fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediamanagementapplication.R
import com.example.mediamanagementapplication.databinding.FragmentFavoriteBinding
import com.example.playit.Adapter.FavoriteAdapter
import com.example.playit.Adapter.MusicAdapter
import com.example.playit.Fectory.MediaFectory
import com.example.playit.Interface.onAudioClick
import com.example.playit.Model.Music
import com.example.playit.Repository.MusicRepository
import com.example.playit.RoomDB.MusicDatabase
import com.example.playit.ViewModel.MusicViewModel

class FavoriteFragment : Fragment(),onAudioClick{
    private var binding: FragmentFavoriteBinding?=null
    private var recyclerView:RecyclerView?=null
    private var musicViewModel:MusicViewModel?=null
    private  var mediaPlayer: MediaPlayer?=null
    private var isPlaying:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // in here you can do logic when backPress is clicked
                requireActivity().finish()
                onDestroy()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentFavoriteBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        recyclerView=binding?.recyclerview
        val db= MusicDatabase.getDatabase(requireContext())
        try {
            val musicRepository=MusicRepository(requireContext(),db)
            musicViewModel=ViewModelProvider(this,MediaFectory(musicRepository)).get(MusicViewModel::class.java)
            musicViewModel?.getAudios()
            recyclerView?.layoutManager=LinearLayoutManager(requireContext())
            musicViewModel?.videordb?.observe(requireActivity(), Observer {
                recyclerView?.adapter=FavoriteAdapter(requireContext(),it,this)
            })
        }catch (e:java.lang.Exception){
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
            Log.d("ooo",e.message.toString())
        }
        return binding?.root
    }

    override fun onAudioItemClick(position: Int, music: Music) {
        try {
            val bundle=Bundle()
            bundle.putString("title",music.title)
            bundle.putString("album",music.album)
            bundle.putString("duration",music.duration.toString())
            bundle.putString("path",music.path)
            //findNavController().navigate(R.id.playerFragment,bundle)
        }catch (e:java.lang.Exception){
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAudioItemLongClick(position: Int, music: Music) {
        try {
            val builder= AlertDialog.Builder(requireContext())
            val view=LayoutInflater.from(requireContext()).inflate(R.layout.pop_dialogue,null)
            builder.setView(view)
            val alert=builder.create()
            alert.show()
            val yes=view.findViewById<Button>(R.id.button)
            val no=view.findViewById<Button>(R.id.button1)

            yes.setOnClickListener {
                musicViewModel?.deleteMusic(music)
                alert.dismiss()
            }
            no.setOnClickListener {
                alert.dismiss()
            }

            Log.d("ooo","$position!!${music.title}")
        }
        catch (e:Exception){

        }
    }

}