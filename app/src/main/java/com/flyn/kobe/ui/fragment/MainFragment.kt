package com.flyn.kobe.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.palette.graphics.Palette
import com.flyn.kobe.R
import com.flyn.kobe.databinding.FragmentMainBinding
import com.flyn.kobe.ui.activity.HostActivity
import com.flyn.kobe.ui.adapter.BannerImageAdapter
import com.flyn.kobe.ui.viewmodel.MainViewModel
import com.flyn.kobe.utils.Util
import com.flyn.kobe.utils.statusbar.StatusBarUtil
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<MainViewModel> { defaultViewModelProviderFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initView()
        initData()
        setListener()
    }

    private fun setListener() {
        viewModel.bannerData.observe(viewLifecycleOwner, { it ->
            val list = ArrayList<String>()
            it.forEach {
                list.add(it.image)
            }
            val adapter = BannerImageAdapter(list)
            binding.banner.let {
                it.addBannerLifecycleObserver(this)
                it.indicator = CircleIndicator(activity)
                it.adapter = adapter
            }
            adapter.drawableLiveData.observe(viewLifecycleOwner, {
                it?.let {
                    val palette = Palette.from(it).generate()
                    val isLightColor = Util.isLightColor(palette.getDominantColor(ContextCompat.getColor(activity as Context, R.color.colorPrimary)))
//                    actionBarDrawerToggle.drawerArrowDrawable.color = ContextCompat.getColor(this, if (isLightColor) R.color.black else R.color.white)
                }
            })
        })
    }

    private fun initView() {
        toolbar.title = "首页"
        val hostActivity = activity as HostActivity
        hostActivity.setSupportActionBar(toolbar)

        StatusBarUtil.setStatusBarColorForCollapsingToolbar(activity, binding.appbar, binding.collapsingLayout, toolbar, ContextCompat.getColor(activity as Context, R.color.colorPrimary))
        binding.collapsingLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity as Context, R.color.white))

        val navController = hostActivity.getNavController()
        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        binding.collapsingLayout.setupWithNavController(toolbar, navController, appBarConfiguration)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initData() {
        viewModel.getBannerData()
    }


}