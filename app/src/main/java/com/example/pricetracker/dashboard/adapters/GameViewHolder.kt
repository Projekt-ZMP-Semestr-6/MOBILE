package com.example.pricetracker.dashboard.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pricetracker.data.models.GameModel
import com.example.pricetracker.databinding.GameItemBinding
import java.text.SimpleDateFormat
import java.util.*

class GameViewHolder(private val binding: GameItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(game: GameModel) {
        binding.tvId.text = "Id: ${game.id}"
        binding.tvAppId.text = "App Id: ${game.appid}"
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(game.last_modified * 1000)
        binding.tvLastModified.text = "Last modified: $date"
        binding.tvNameTitle.text = game.name

        Glide.with(binding.root)
            .load(game.header_img)
            .apply(RequestOptions().transform(RoundedCorners(16)))
            .into(binding.ivGameThumbnail)
    }
}