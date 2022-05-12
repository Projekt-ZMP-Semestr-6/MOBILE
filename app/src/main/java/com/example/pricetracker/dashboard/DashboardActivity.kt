package com.example.pricetracker.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pricetracker.R
import com.example.pricetracker.authentication.AuthenticationActivity
import com.example.pricetracker.data.network.UIState
import com.example.pricetracker.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private val viewModel: DashboardViewModel by viewModels()
    private var binding: ActivityDashboardBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_dashboard) as NavHostFragment
        val navController = navHostFragment.navController
        binding?.bottomNavView?.apply {
            this.setupWithNavController(navController)
        }
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_activity_dashboard)
        return navController.navigateUp()
    }

    override fun onStart() {
        super.onStart()
        viewModel.uiState.observe(this) {
            it?.let { state ->
                binding?.pbLoading?.visibility = View.GONE
                when (state) {
                    UIState.OnWaiting -> {
                    }
                    UIState.OnSuccess -> {
                    }
                    UIState.OnFailure -> {
                    }
                    UIState.InProgress -> binding?.pbLoading?.visibility = View.VISIBLE
                    is UIState.OnHttpFailure -> {
                    }
//                        snackBarExtension(viewModel.errorMessage.value.toString())
                }
            }
        }
        viewModel.userLoggedIn.observe(this) { loggedIn ->
            if (!loggedIn) {
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}