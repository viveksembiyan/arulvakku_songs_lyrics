package com.arulvakku.lyrics.app.ui.view.lyrics

import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.arulvakku.lyrics.app.R
import com.arulvakku.lyrics.app.databinding.LyricsFragmentBinding
import com.arulvakku.lyrics.app.ui.view.home.song.SongModel
import com.arulvakku.lyrics.app.ui.viewmodels.DatabaseViewModel
import com.arulvakku.lyrics.app.utilities.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LyricsFragment : Fragment() {

    companion object {
        fun newInstance(songModel: SongModel): Fragment {
            val fragment = LyricsFragment()
            val args = Bundle()
            args.putInt("id", songModel.sSongId?:0)
            args.putString("lyrics", songModel.sSong)
            args.putString("title", songModel.sTitle)
            args.putString("category", songModel.sCategory)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: LyricsViewModel
    private lateinit var binding: LyricsFragmentBinding
    private val databaseViewModel: DatabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LyricsFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(LyricsViewModel::class.java)
        binding.textLyrics.text = requireArguments().getString("lyrics")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.lyrics_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_make_favorite -> {
                subscribe()
                true
            }

            R.id.action_make_share -> {
                val shareMsg = getString(
                    R.string.share_message,
                    requireArguments().getString("title"),
                    requireArguments().getString("lyrics")
                )

                val intent = ShareCompat.IntentBuilder.from(requireActivity())
                    .setType("text/plain")
                    .setText(shareMsg)
                    .intent

                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun subscribe() {
        val id = requireArguments().getInt("id")

        databaseViewModel.setFavouriteSongs(id.toLong())
        databaseViewModel.setFavouriteSongsResult.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.LOADING -> {
                    Timber.d("loading...")
                }
                Status.SUCCESS -> {
                    Timber.d("success: ${it.data}")

                }
                Status.ERROR -> {
                    Timber.d("error: ${it.message}")
                }
            }
        }

    }
}