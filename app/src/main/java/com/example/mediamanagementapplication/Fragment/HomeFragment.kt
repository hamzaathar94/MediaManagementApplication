package com.example.playit.Fragment

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediamanagementapplication.R
import com.example.mediamanagementapplication.databinding.FragmentHomeBinding
import com.example.playit.Adapter.MusicAdapter
import com.example.playit.Fectory.MediaFectory
import com.example.playit.Interface.onAudioClick
import com.example.playit.Model.Music
import com.example.playit.Model.formatDuration

import com.example.playit.Repository.MusicRepository
import com.example.playit.RoomDB.MusicDatabase
import com.example.playit.ViewModel.MusicViewModel


class HomeFragment : Fragment(),onAudioClick {
    private var binding: FragmentHomeBinding?=null
    private var recyclerView:RecyclerView?=null
    private var musicViewModel:MusicViewModel?=null
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
        binding= FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()),container,false)

        requestPermissions()
        recyclerView=binding?.recyclerview
        val db= MusicDatabase.getDatabase(requireContext())
        try {
            val musicRepository=MusicRepository(requireContext(),db)
            musicViewModel=ViewModelProvider(this,MediaFectory(musicRepository)).get(MusicViewModel::class.java)
            musicViewModel?.getAudios()
            recyclerView?.layoutManager=GridLayoutManager(requireContext(),2)
            musicViewModel?.getAudioLiveData()?.observe(requireActivity(), Observer {
                recyclerView?.adapter=MusicAdapter(requireContext(),it,this)
            })

        }catch (e:java.lang.Exception){
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
            Log.d("ooo",e.message.toString())
        }

        return binding?.root
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }
    //check permission
    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }
    }




    override fun onAudioItemClick(position: Int, music: Music) {
        try {
           // Toast.makeText(requireContext(), "I'm click", Toast.LENGTH_SHORT).show()
//            val bundle=Bundle()
//            bundle.putString("title",music.title)
//            bundle.putString("album",music.album)
//            bundle.putString("duration",music.duration.toString())
//            bundle.putString("path",music.path)
//            bundle.putString("position",position.toString())
//            findNavController().navigate(R.id.playerFragment,bundle)
        }catch (e:Exception){
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAudioItemLongClick(position: Int, music: Music) {
        try {
            val title=music.title
            val album=music.album
            val duration=music.duration
            val path=music.path
            val d= formatDuration(duration)
            Log.d("mmm","title:"+title)
            Log.d("mmm","album:"+album)
            Log.d("mmm","duration:"+duration)
            Log.d("mmm","path:"+path)
            musicViewModel?.insertMusic(title, album, duration, path)
            if (musicViewModel==null){
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
               // findNavController().navigate(R.id.favoriteFragment)
            }
        }catch (e:Exception){
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}