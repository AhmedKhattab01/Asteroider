package com.example.asteroider.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.asteroider.R
import com.example.asteroider.databinding.ActivityMainBinding
import com.example.asteroider.ui.screens.home.AsteroiderViewModel
import com.example.data.work_manager.MyWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private val asteroiderViewModel: AsteroiderViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set action bar to tool bar
        setSupportActionBar(binding.materialToolbar)
        // initialize nav host fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        // initialize controller
        navController = navHostFragment.navController

        // set actionbar with nav controller
        setupActionBarWithNavController(navController)


        lifecycleScope.launch {
            asteroiderViewModel.getPlanetaryFromLocal().collect {
                if (it == null) {
                    asteroiderViewModel.getPlanetaryFromNetwork()
                }
            }
        }

        lifecycleScope.launch {
            asteroiderViewModel.getNeoFromLocal().collect {
                if (it.isNullOrEmpty()) {
                    asteroiderViewModel.getNeoFromNetwork()
                }
            }
        }

        val workerConstraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.DAYS)
            .setConstraints(workerConstraint)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("Refresh Worker",ExistingPeriodicWorkPolicy.KEEP,workRequest)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}