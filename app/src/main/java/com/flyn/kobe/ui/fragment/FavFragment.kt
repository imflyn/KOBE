package com.flyn.kobe.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.flyn.kobe.R
import com.flyn.kobe.databinding.FragmentFavBinding
import com.flyn.kobe.ui.activity.HostActivity
import com.flyn.kobe.utils.Util
import com.flyn.kobe.utils.statusbar.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_main.*

class FavFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentFavBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
        setListener()
    }

    private fun initView() {
        toolbar.title = "收藏"
        (toolbar.layoutParams as ConstraintLayout.LayoutParams).topMargin = Util.getStatusBarHeight()

        val hostActivity = activity as HostActivity
        hostActivity.setSupportActionBar(toolbar)


        StatusBarUtil.setStatusBarColor(hostActivity, ContextCompat.getColor(hostActivity, R.color.colorPrimary))

        val navController = hostActivity.getNavController()
        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initData() {

    }


    private fun setListener() {

    }
}