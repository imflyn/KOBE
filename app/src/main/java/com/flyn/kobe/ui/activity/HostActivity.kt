package com.flyn.kobe.ui.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.flyn.kobe.R
import com.flyn.kobe.databinding.ActivityHostBinding
import com.flyn.kobe.ui.viewmodel.HostViewModel


class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel by viewModels<HostViewModel> { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initData()
        setListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setListener() {
        viewModel.navHeaderUrl.observe(this, {
            Glide.with(this)
                .load(it)
                .placeholder(R.color.grey_DD)
                .error(R.color.grey_DD)
                .centerCrop()
                .into(binding.navigationView.getHeaderView(0) as ImageView)
        })
    }

    private fun initView() {
        val navController = getNavController()
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).setOpenableLayout(binding.drawerLayout).build()
        binding.navigationView.setupWithNavController(navController)
    }

    private fun initData() {
        viewModel.getVersion()
        viewModel.getHeaderUrl()
    }

    fun getNavController(): NavController {
        return (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
    }

    fun getAppBarConfiguration(): AppBarConfiguration {
        return appBarConfiguration
    }
}


