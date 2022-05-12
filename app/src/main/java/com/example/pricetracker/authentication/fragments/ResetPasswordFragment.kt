package com.example.pricetracker.authentication.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pricetracker.R
import com.example.pricetracker.authentication.AuthenticationActivity
import com.example.pricetracker.authentication.AuthenticationViewModel
import com.example.pricetracker.dashboard.DashboardActivity
import com.example.pricetracker.data.network.UIState
import com.example.pricetracker.databinding.FragmentResetPasswordBinding
import com.example.pricetracker.utils.snackBarExtension

class ResetPasswordFragment : Fragment() {
    private val viewModel: AuthenticationViewModel by activityViewModels()
    private var _binding: FragmentResetPasswordBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtonsListener()
        initUiStateObserver()
        fakeUserInputs()
    }

    private fun initButtonsListener() {
        _binding?.apply {
            btnResetPassword.setOnClickListener {
                viewModel.resetPassword(
                    tietResetPasswordEmail.text.toString()
                )
            }
        }
    }

    private fun initUiStateObserver() {
        viewModel.uiState.observe(this) {
            it?.let { state ->
                when (state) {
                    UIState.OnSuccess -> {
                        viewModel.clearUIState()
                        requireActivity().snackBarExtension("Reset link sent. Please check your email.")
                        Handler(Looper.getMainLooper()).postDelayed(
                            { findNavController().navigateUp() },
                            500
                        )
                    }
                }
            }
        }
    }

    private fun fakeUserInputs() {
        if((requireActivity() as AuthenticationActivity).FAKE_USER_INPUTS){
            _binding?.apply {
                tietResetPasswordEmail.setText(R.string.test_user_email_address)
            }
        }
    }
}