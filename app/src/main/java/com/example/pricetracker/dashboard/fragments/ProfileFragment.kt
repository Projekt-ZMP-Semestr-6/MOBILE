package com.example.pricetracker.dashboard.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pricetracker.R
import com.example.pricetracker.dashboard.DashboardViewModel
import com.example.pricetracker.databinding.FragmentProfileBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class ProfileFragment : Fragment() {
    private val viewModel: DashboardViewModel by activityViewModels()
    private var _binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false).apply {
            viewModel = this@ProfileFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        initUserDataObserver()
        viewModel.getUserData()
    }

    private fun initButtons() {
        _binding?.apply {
            btnEditOrSaveName.setOnClickListener {
                when (btnEditOrSaveName.text) {
                    getString(R.string.edit_button_text) -> {
                        viewModel?.editOrSaveProfile?.value = getString(R.string.save_button_text)
                        viewModel?.profileEditable?.value = InputType.TYPE_CLASS_TEXT
                    }
                    else -> {
                        viewModel?.editOrSaveProfile?.value = getString(R.string.edit_button_text)
                        viewModel?.profileEditable?.value = InputType.TYPE_NULL
                        val name = this.etUserName.text.toString()
                        this@ProfileFragment.viewModel.updateUserName(name)
                    }
                }
            }
            btnEditEmail.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToEditEmailAddressDialog())
            }
            btnEditPassword.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToDialogNewPassword())
            }
            btnLogout.setOnClickListener {
                viewModel?.logout()
            }
        }
    }

    private fun initUserDataObserver(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}