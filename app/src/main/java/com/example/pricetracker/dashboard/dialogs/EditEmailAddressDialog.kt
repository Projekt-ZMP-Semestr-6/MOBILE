package com.example.pricetracker.dashboard.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.pricetracker.dashboard.DashboardViewModel
import com.example.pricetracker.databinding.DialogEditEmailAddressBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class EditEmailAddressDialog : DialogFragment() {
    private val viewModel: DashboardViewModel by activityViewModels()
    private var _binding: DialogEditEmailAddressBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogEditEmailAddressBinding.inflate(inflater, container, false).apply {
            viewModel = this@EditEmailAddressDialog.viewModel
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

            btnEditNewEmailSave.setOnClickListener {
                val email = tietNewEmailAddress.text.toString()
                val password = tietPassword.text.toString()
                if (email.isNotEmpty() && email.isNotEmpty())
                    viewModel?.updateUserEmailAddress(
                        email,
                        password
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