package com.arulvakku.lyrics.app.ui.view.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arulvakku.lyrics.app.databinding.LayoutCategoryRowItemBinding
import com.arulvakku.lyrics.app.ui.listeners.CellClickListenerCategory
import com.arulvakku.lyrics.app.ui.view.home.model.SongCategoryModel


class CategoryAdapter(
    var context: Context,
    private val categoryItems: List<SongCategoryModel>,
    private val cellClickListenerSongs: CellClickListenerCategory
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int = categoryItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryItems[position]
        holder.bind(category, position)
        holder.itemView.setOnClickListener {
            cellClickListenerSongs.onCategoryItemClickListener(
                categoryItems.get(position),
                position
            )
        }
    }


    class ViewHolder private constructor(private val binding: LayoutCategoryRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(categoriesResult: SongCategoryModel, position: Int) {
            binding.txtCategoryTitle.text = categoriesResult.sCategory
            val rowCount = position + 1
            binding.txtCount.text = "$rowCount."
            binding.textCategoryName.visibility = View.GONE
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutCategoryRowItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}