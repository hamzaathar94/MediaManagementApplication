package com.example.playit.Interface

import com.example.playit.Model.Music

interface onAudioClick {
    fun onAudioItemClick(position:Int,music: Music)
    fun onAudioItemLongClick(position: Int,music: Music)
}