package com.example.playit.Repository

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playit.Model.Music
import com.example.playit.RoomDB.MusicDatabase

class MusicRepository(val context: Context,private val musicDatabase: MusicDatabase) {
    val musicLiveData=MutableLiveData<List<Music>>()

    fun getMusic(){
        val audios= mutableListOf<Music>()
        val sortOrder="${MediaStore.Audio.Media.DATE_ADDED}DESC"
        val cursor=context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        null,null,null,null)
        if (cursor!=null && cursor.moveToFirst()){
            do {
                val title=
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                val album=
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
                val artist=
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                val duration=
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                val path=
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                audios.add(Music(null,title,album,duration.toLong(),path))
            }while (cursor.moveToNext())
            cursor.close()
        }
        musicLiveData.postValue(audios)
    }

    fun getAudiosLiveData(): LiveData<List<Music>> {
        return musicLiveData
    }

    //
    fun getallMusic(): LiveData<List<Music>> {
        return musicDatabase.getMusicDao().getAllMusic()
    }


    suspend fun insertmusic(music: Music) {
        musicDatabase.getMusicDao().insert(music)
    }

    fun deleteMusic(music: Music) {
        musicDatabase.getMusicDao().deletemusic(music)
    }

    fun search(name: String): LiveData<List<Music>> {
        return musicDatabase.getMusicDao().search(name)
    }
}