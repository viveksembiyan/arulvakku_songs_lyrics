package com.arulvakku.lyrics.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.arulvakku.lyrics.app.data.model.Song
import com.arulvakku.lyrics.app.utilities.getSongs

class LyricsViewModel : ViewModel() {

    lateinit var songsResult: Song

    init {
        getSongCategories()
    }

    private fun getSongCategories() {
        songsResult = getSongs();
    }
}