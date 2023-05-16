package com.example.playit.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playit.Model.Music
import com.example.playit.Repository.MusicRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MusicViewModel(private val musicRepository: MusicRepository):ViewModel(){
    val audios: MutableLiveData<List<Music>> = musicRepository.musicLiveData
    val videordb: LiveData<List<Music>> = musicRepository.getallMusic()

    fun getAudios() {
        musicRepository.getMusic()
    }

    fun getAudioLiveData(): LiveData<List<Music>> {
        musicRepository.getAudiosLiveData()
        return audios
    }

    //room
    fun insertMusic(title: String,album:String,duration:Long, path: String) {
        GlobalScope.launch {
            val music=Music(null, title,album,duration,path)
            musicRepository.insertmusic(music)
        }
    }

    fun deleteMusic(music: Music) {
        GlobalScope.launch {
            musicRepository.deleteMusic(music)
        }
    }

    fun search(title: String): LiveData<List<Music>> {
        return musicRepository.search(title)
    }
}