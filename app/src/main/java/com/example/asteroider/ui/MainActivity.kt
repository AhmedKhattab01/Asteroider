package com.example.asteroider.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.Constraints.Builder
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.asteroider.R
import com.example.asteroider.databinding.ActivityMainBinding
import com.example.asteroider.ui.screens.home.NeoViewModel
import com.example.data.work_manager.MyWorker
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel : NeoViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
        val constraints = Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val twelveAm = LocalTime.of(0, 0)
        val now = LocalTime.now()

        val initialDelay = if (now.isAfter(twelveAm)) {
            ChronoUnit.HOURS.between(now, twelveAm.plusHours(24))
        } else {
            ChronoUnit.HOURS.between(now, twelveAm)
        }

        val workRequest = PeriodicWorkRequestBuilder<MyWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}