package com.flyn.kobe.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.ui.setupWithNavController
import androidx.palette.graphics.Palette
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flyn.kobe.R
import com.flyn.kobe.bean.CategoryData
import com.flyn.kobe.databinding.FragmentMainBinding
import com.flyn.kobe.ui.activity.HostActivity
import com.flyn.kobe.ui.adapter.BannerImageAdapter
import com.flyn.kobe.ui.viewmodel.MainViewModel
import com.flyn.kobe.utils.Util
import com.flyn.kobe.utils.statusbar.StatusBarUtil
import com.google.android.material.tabs.TabLayoutMediator
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
            val adapter = BannerImageAdapter(it)
            binding.banner.let {
                it.addBannerLifecycleObserver(this)
                it.indicator = CircleIndicator(activity)
                it.adapter = adapter
            }
            adapter.drawableLiveData.observe(viewLifecycleOwner, {
                it?.let {
                    val palette = Palette.from(it).generate()
                    val isLightColor = Util.isLightColor(palette.getDominantColor(ContextCompat.getColor(activity as Context, R.color.colorPrimary)))
                    binding.collapsingLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity as Context, if (isLightColor) R.color.black else R.color.white))
                    binding.toolbar.setNavigationIcon(if (isLightColor) R.drawable.ic_menu_24_black else R.drawable.ic_menu_24_white)
                }
            })
        })
        viewModel.categoryData.observe(viewLifecycleOwner, {
            (binding.viewPager.adapter as ChildFragmentStateAdapter).data = ArrayList(it)
            binding.viewPager.adapter?.notifyDataSetChanged()
        })
    }

    private fun initView() {
        toolbar.title = "首页"
        val hostActivity = activity as HostActivity

        StatusBarUtil.setStatusBarColorForCollapsingToolbar(activity, binding.appbar, binding.collapsingLayout, toolbar, ContextCompat.getColor(activity as Context, R.color.colorPrimary))

        val navController = hostActivity.getNavController()
        val appBarConfiguration = hostActivity.getAppBarConfiguration()
        binding.collapsingLayout.setupWithNavController(toolbar, navController, appBarConfiguration)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.collapsingLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity as Context, R.color.white))
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu_24_white)

        binding.viewPager.adapter = ChildFragmentStateAdapter(this)
        val tabLayoutMediator = TabLayoutMediator(tabLayout, binding.viewPager) { tab, position ->
            tab.text = (binding.viewPager.adapter as ChildFragmentStateAdapter).data[position].title
        }
        tabLayoutMediator.attach()
    }

    private fun initData() {
        viewModel.getBannerData()
        viewModel.getCategoryData()
    }

    class ChildFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        var data = ArrayList<CategoryData>()

        override fun getItemCount(): Int = data.size

        override fun createFragment(position: Int): Fragment {
            return ArticleListFragment(data[position])
        }

    }
}