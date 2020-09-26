package com.flyn.kobe.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.flyn.kobe.databinding.FragmentArticleBinding
import com.flyn.kobe.ui.activity.HostActivity
import com.flyn.kobe.utils.Util
import kotlinx.android.synthetic.main.fragment_main.*

class ArticleFragment : Fragment() {

    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentArticleBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initData()
        setListener()
    }


    private fun initView() {
        toolbar.title = args.title
        (toolbar.layoutParams as LinearLayout.LayoutParams).topMargin = Util.getStatusBarHeight()

        val hostActivity = activity as HostActivity
        hostActivity.setSupportActionBar(toolbar)

        val navController = hostActivity.getNavController()
        val appBarConfiguration = hostActivity.getAppBarConfiguration()
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initData() {
        binding.webView.loadUrl(args.url)
    }

    private fun setListener() {

    }

}