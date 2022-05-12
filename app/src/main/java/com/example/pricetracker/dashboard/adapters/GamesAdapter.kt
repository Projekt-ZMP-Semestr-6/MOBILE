package com.example.pricetracker.dashboard.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.pricetracker.dashboard.DashboardViewModel
import com.example.pricetracker.dashboard.fragments.HomeFragment
import com.example.pricetracker.data.models.GameModel
import com.example.pricetracker.databinding.GameItemBinding
import kotlinx.coroutines.InternalCoroutinesApi

@SuppressLint("NotifyDataSetChanged")
@InternalCoroutinesApi
class GamesAdapter(
    fragment: HomeFragment,
    viewModel: DashboardViewModel,
    val onGameSelected: (Int) -> Unit
) : RecyclerView.Adapter<GameViewHolder>() {
    private var gamesList: List<GameModel> = listOf()

    init {
        viewModel.bestsellersList.observe(fragment.viewLifecycleOwner, Observer { list ->
            list?.let {
                gamesList = it
                notifyDataSetChanged()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameItemBinding.inflate(LayoutInflater.from(parent.context))
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onGameSelected(position)
        }
        holder.bind(gamesList[position])
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }
}