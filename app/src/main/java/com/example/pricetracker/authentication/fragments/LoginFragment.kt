package com.example.pricetracker.authentication.fragments

import android.content.Intent
import android.os.Bundle
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
import com.example.pricetracker.databinding.FragmentLoginBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class LoginFragment : Fragment() {
    private val viewModel: AuthenticationViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonsListeners()
        initUiStateObserver()
        fakeUserInputs()
    }

    private fun initButtonsListeners() {
        _binding?.apply {
            btnLogin.setOnClickListener {
                viewModel.login(
                    tietLoginEmail.text.toString(),
                    tietPassword.text.toString()
                )
            }
            btnGoToRegistration.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
            }
            tvForgotPassword.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
            }
        }
    }

    private fun initUiStateObserver() {
        viewModel.uiState.observe(this) {
            it?.let { state ->
                when (state) {
                    UIState.OnSuccess -> {
                        val intent = Intent(context, DashboardActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun fakeUserInputs() {
        if((requireActivity() as AuthenticationActivity).FAKE_USER_INPUTS){
            _binding?.apply {
                tietLoginEmail.setText(R.string.test_user_email_address)
                tietPassword.setText(R.string.test_user_password)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}