package com.example.player.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.player.data.entity.MusicModel
import javax.inject.Inject
import javax.inject.Singleton

interface MainRepository {

    var allMusicList: MutableList<MusicModel>
    suspend fun getAllMusicsFromInternal()

}