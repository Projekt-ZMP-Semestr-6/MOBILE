package com.example.pricetracker.dashboard.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.pricetracker.dashboard.DashboardViewModel
import com.example.pricetracker.databinding.DialogEditEmailAddressBinding
import com.example.pricetracker.databinding.DialogNewPasswordBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class DialogNewPassword : DialogFragment() {
    private val viewModel: DashboardViewModel by activityViewModels()
    private var _binding: DialogNewPasswordBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogNewPasswordBinding.inflate(inflater, container, false).apply {
            viewModel = this@DialogNewPassword.viewModel
        }
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
        initObserver()
    }

    private fun initView() {
        dialog?.apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setCancelable(false)
        }
    }

    private fun initListeners() {
        _binding?.apply {
            btnCloseDialog.setOnClickListener {
                dismiss()
            }

            btnNewPasswordSave.setOnClickListener {
                val oldPassword = tietOldPassword.text.toString()
                val newPassword = tietNewPassword.text.toString()
                val repeatNewPassword = tietNewPasswordRepeat.text.toString()
                if (oldPassword.isNotEmpty() && newPassword == repeatNewPassword)
                    viewModel?.updateUserPassword(
                        oldPassword,
                        newPassword
                    )
            }
        }
    }

    private fun initObserver() {
        viewModel.closeDialog.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    viewModel.closeDialog.value = false
                    dialog?.dismiss()
                }
            }
        }
    }
}