package com.example.pricetracker.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pricetracker.dashboard.DashboardViewModel
import com.example.pricetracker.databinding.FragmentNotificationsBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class NotificationsFragment : Fragment() {
    private val viewModel: DashboardViewModel by activityViewModels()
    private var _binding: FragmentNotificationsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}