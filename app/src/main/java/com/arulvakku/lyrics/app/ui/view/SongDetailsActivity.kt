package com.arulvakku.lyrics.app.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.arulvakku.lyrics.app.databinding.ActivitySongDetailsBinding
import com.arulvakku.lyrics.app.ui.adapters.LyricsPagerAdapter
import com.arulvakku.lyrics.app.ui.view.home.song.SongModel
import com.arulvakku.lyrics.app.ui.view.lyrics.LyricsViewModel
import com.arulvakku.lyrics.app.utilities.SongsSingleton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongDetailsActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: LyricsPagerAdapter
    private lateinit var binding: ActivitySongDetailsBinding
    private lateinit var viewModel: LyricsViewModel
    private var songModel: SongModel? = null

    var position: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this).get(LyricsViewModel::class.java)
        songModel = intent.getSerializableExtra("song") as SongModel?
        position = intent.getIntExtra("pos", 0)


        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }


    private fun setAdapter() {
        pagerAdapter = LyricsPagerAdapter(supportFragmentManager, SongsSingleton.getSongs())
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.currentItem = position
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

}