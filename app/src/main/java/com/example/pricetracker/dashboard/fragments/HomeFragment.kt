package com.example.pricetracker.dashboard.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pricetracker.dashboard.DashboardViewModel
import com.example.pricetracker.dashboard.adapters.GamesAdapter
import com.example.pricetracker.databinding.FragmentHomeBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class HomeFragment : Fragment() {
    private val viewModel: DashboardViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: GamesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter(){
        Log.i("MyTag", "initAdapter  ")
        adapter = GamesAdapter(this, viewModel) {
            //TODO open details
//            navigateToDetailFragment(it)
        }
        _binding?.rvBestsellers?.adapter = adapter
        viewModel.getBestsellers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}