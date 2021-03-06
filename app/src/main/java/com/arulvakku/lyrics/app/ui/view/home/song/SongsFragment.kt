package com.arulvakku.lyrics.app.ui.view.home.song

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arulvakku.lyrics.app.databinding.SongsFragmentBinding
import com.arulvakku.lyrics.app.ui.listeners.CellClickListenerSongs
import com.arulvakku.lyrics.app.ui.view.home.song.adapter.SongsAdapter
import com.arulvakku.lyrics.app.ui.view.home.model.SongCategoryModel
import com.arulvakku.lyrics.app.ui.view.SongDetailsActivity
import com.arulvakku.lyrics.app.ui.viewmodels.DatabaseViewModel
import com.arulvakku.lyrics.app.utilities.Status
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SongsFragment : Fragment(), CellClickListenerSongs {

    companion object {
        fun newInstance() = SongsFragment()
    }

    private lateinit var viewModel: SongsViewModel
    private lateinit var binding: SongsFragmentBinding

    private val databaseViewModel: DatabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SongsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SongsViewModel::class.java)
        subscribe()
    }


    private fun setAdapter(list: List<SongModel>) {
//        SongsSingleton.setSongs(list as ArrayList<SongModel>)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = activity?.let {
                SongsAdapter(
                    it,
                    list,
                    this@SongsFragment
                )
            }
        }
    }

    private fun subscribe() {
        databaseViewModel.getSongs()
        databaseViewModel.songsResult.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.LOADING -> {
                    Timber.d("loading...")
                }
                Status.SUCCESS -> {
                    Timber.d("success: ${it.data}")

                    setAdapter(it.data ?: emptyList())
                }
                Status.ERROR -> {
                    Timber.d("error: ${it.message}")
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        Timber.d("SongsFragment")
    }

    override fun onSongCellClickListener(item: SongModel, position: Int) {
        val intent = Intent(context, SongDetailsActivity::class.java)
        val bundle = Bundle().apply {
            putSerializable("song", item)
        }
        bundle.putInt("pos", position)
        intent.putExtras(bundle)
        startActivity(intent)
    }


}