package com.example.playit.Fectory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playit.Repository.MusicRepository
import com.example.playit.ViewModel.MusicViewModel

class MediaFectory(val musicRepository: MusicRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MusicViewModel(musicRepository) as T
    }
}