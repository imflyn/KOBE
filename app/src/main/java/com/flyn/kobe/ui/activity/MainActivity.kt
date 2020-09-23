package com.flyn.kobe.ui.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.flyn.kobe.R
import com.flyn.kobe.databinding.ActivityMainBinding
import com.flyn.kobe.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel


        initView()
        initData()
        setListener()
    }

    private fun setListener() {
        viewModel.navHeaderUrl.observe(this, {
            Glide.with(this)
                .load(it)
                .placeholder(R.mipmap.icon_image_default)
                .error(R.mipmap.icon_image_default)
                .centerCrop()
                .into(binding.navigationView.getHeaderView(0) as ImageView)
        })
    }

    private fun initView() {
        viewModel.getVersion(this)
        viewModel.getHeaderUrl()
    }

    private fun initData() {

    }

}


