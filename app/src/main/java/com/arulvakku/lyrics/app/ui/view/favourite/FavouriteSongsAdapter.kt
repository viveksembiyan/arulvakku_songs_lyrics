package com.arulvakku.lyrics.app.ui.view.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arulvakku.lyrics.app.databinding.LayoutFavouriteSongItemBinding
import com.arulvakku.lyrics.app.ui.view.home.song.cache.SongCacheEntity

class FavouriteSongsAdapter(fragment: FavouriteFragment) : RecyclerView.Adapter<FavouriteSongsAdapter.MyViewHolder>() {

    val onClick: OnClick = fragment
    val list = mutableListOf<SongCacheEntity>()

    fun update(list: List<SongCacheEntity>,textView: TextView) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()

        textView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): FavouriteSongsAdapter.MyViewHolder {
        val view =
                LayoutFavouriteSongItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteSongsAdapter.MyViewHolder, position: Int) {

        holder.bind(list[position], position, onClick)
    }

    class MyViewHolder(binding: LayoutFavouriteSongItemBinding) :
            RecyclerView.ViewHolder(binding.root) {


        val view = binding
        fun bind(data: SongCacheEntity, position: Int, onClick: OnClick) {
            view.textViewNumber.text = "${(position + 1)}. "
            view.textViewSongTitle.text = data.sTitle
            view.textViewSongDesc.text = data.sSong

            view.imageViewFavourite.setOnClickListener {
                val id: Int = data.sSongId ?: 0
                onClick.onClick(id, position)
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun remove(position: Int,textView: TextView) {
        list.removeAt(position)
        notifyDataSetChanged()

        textView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    interface OnClick {
        fun onClick(id: Int, position: Int)
    }
}