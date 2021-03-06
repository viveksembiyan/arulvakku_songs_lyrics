package com.arulvakku.lyrics.app.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.arulvakku.lyrics.app.ui.view.home.song.SongModel
import com.arulvakku.lyrics.app.ui.view.lyrics.LyricsFragment

class LyricsPagerAdapter(fragmentManager: FragmentManager, private val songs: List<SongModel>) : FragmentStatePagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return LyricsFragment.newInstance(songs[position])
    }

    override fun getCount(): Int {
        return songs.size
    }
}