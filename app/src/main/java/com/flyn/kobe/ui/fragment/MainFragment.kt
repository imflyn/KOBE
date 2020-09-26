package com.flyn.kobe.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flyn.kobe.R
import com.flyn.kobe.bean.BannerData
import com.flyn.kobe.bean.CategoryData
import com.flyn.kobe.databinding.FragmentMainBinding
import com.flyn.kobe.ui.activity.HostActivity
import com.flyn.kobe.ui.adapter.BannerImageAdapter
import com.flyn.kobe.ui.viewmodel.MainViewModel
import com.flyn.kobe.utils.statusbar.StatusBarUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.youth.banner.indicator.CircleIndicator


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private var rootView: View? = null
    private val viewModel by viewModels<MainViewModel> { defaultViewModelProviderFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            binding = FragmentMainBinding.inflate(inflater)
            rootView = binding.root
            binding.viewModel = viewModel
            initView()
            initData()
            setListener()
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        binding.collapsingLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity as Context, R.color.white))
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu_24_white)
    }

    private fun setListener() {
        viewModel.bannerData.observe(viewLifecycleOwner, { it ->
            val adapter = BannerImageAdapter(it)
            binding.banner.let {
                it.addBannerLifecycleObserver(this)
                it.indicator = CircleIndicator(activity)
                it.adapter = adapter
                it.setOnBannerListener { data, position ->
                    val banner = data as BannerData
                    val action = MainFragmentDirections.actionMainFragmentToArticleFragment(banner.url, banner.title, banner.image)
                    findNavController().navigate(action)
                }
            }
        })
        viewModel.categoryData.observe(viewLifecycleOwner, {
            (binding.viewPager.adapter as ChildFragmentStateAdapter).data = ArrayList(it)
            binding.viewPager.adapter?.notifyDataSetChanged()
            binding.viewPager.offscreenPageLimit = if (it.isNotEmpty()) it.size else 1
        })
    }

    private fun initView() {
        binding.toolbar.title = "首页"
        val hostActivity = activity as HostActivity

        StatusBarUtil.setStatusBarColorForCollapsingToolbar(activity, binding.appbar, binding.collapsingLayout, binding.toolbar, ContextCompat.getColor(activity as Context, R.color.colorPrimary))

        val navController = hostActivity.getNavController()
        val appBarConfiguration = hostActivity.getAppBarConfiguration()
        binding.collapsingLayout.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.collapsingLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity as Context, R.color.white))
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu_24_white)

        binding.viewPager.adapter = ChildFragmentStateAdapter(this)
        binding.viewPager.isSaveEnabled = false
        val tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = (binding.viewPager.adapter as ChildFragmentStateAdapter).data[position].title
        }
        tabLayoutMediator.attach()
    }

    private fun initData() {
        if (viewModel.bannerData.value!!.isEmpty()) {
            viewModel.getBannerData()
        }
        if (viewModel.categoryData.value!!.isEmpty()) {
            viewModel.getCategoryData()
        }
    }

    class ChildFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        var data = ArrayList<CategoryData>()

        override fun getItemCount(): Int = data.size

        override fun createFragment(position: Int): Fragment {
            return ArticleListFragment(data[position])
        }

    }
}