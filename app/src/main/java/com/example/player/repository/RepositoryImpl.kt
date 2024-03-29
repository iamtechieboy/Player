package com.example.player.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.example.player.data.MusicDao
import com.example.player.data.entity.MusicModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class RepositoryImpl @Inject constructor(
    private val musicDao: MusicDao,
    @ApplicationContext private val context: Context
) : MainRepository {

    private var _allMusicList = mutableListOf<MusicModel>()

    suspend fun insert(musicModel: MusicModel) {
        withContext(Dispatchers.IO) {
            musicDao.insert(musicModel)
        }
    }

    override var allMusicList: MutableList<MusicModel>
        get() = _allMusicList
        set(value) {}


    @SuppressLint("Recycle", "Range")
    override suspend fun getAllMusicsFromInternal() {
        try {
            val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val title: String =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val artist: String =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val fullUri: String =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    _allMusicList.add(
                        MusicModel(
                            title = title,
                            artist = artist,
                            url_music = fullUri,
                        )
                    )
                } while (cursor.moveToNext())
            }
        } catch (ex: Exception) {

        }
    }

}