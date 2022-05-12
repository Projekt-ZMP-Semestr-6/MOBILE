package com.example.pricetracker.authentication

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.pricetracker.R
import com.example.pricetracker.data.network.UIState
import com.example.pricetracker.databinding.ActivityAuthenticationBinding
import com.example.pricetracker.utils.snackBarExtension
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    val FAKE_USER_INPUTS = true
    private lateinit var binding: ActivityAuthenticationBinding
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.app_name)
        title = ""

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_authentication) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.login_fragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onStart() {
        super.onStart()
        viewModel.uiState.observe(this) {
            it?.let { state ->
                binding.pbLoading.visibility = View.GONE
                when (state) {
                    UIState.OnWaiting -> {
                    }
                    UIState.OnSuccess -> {
                    }
                    UIState.OnFailure -> {
                    }
                    UIState.InProgress -> binding.pbLoading.visibility = View.VISIBLE
                    is UIState.OnHttpFailure ->
                        snackBarExtension(viewModel.errorMessage.value.toString())
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_activity_authentication)
        return navController.navigateUp()
    }
}