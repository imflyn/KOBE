package com.flyn.kobe.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.flyn.kobe.R
import com.flyn.kobe.databinding.FragmentArticleBinding
import com.flyn.kobe.ui.activity.HostActivity
import com.flyn.kobe.ui.viewmodel.ArticleViewModel
import com.flyn.kobe.utils.Util
import kotlinx.android.synthetic.main.fragment_main.*


class ArticleFragment : Fragment() {

    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var binding: FragmentArticleBinding
    private val viewModel by viewModels<ArticleViewModel> { defaultViewModelProviderFactory }

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
        setHasOptionsMenu(true)

        val navController = hostActivity.getNavController()
        val appBarConfiguration = hostActivity.getAppBarConfiguration()
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initData() {
        binding.webView.loadUrl(args.url)
        viewModel.getFav(args.url)
    }

    private fun setListener() {
        viewModel.favBean.observe(viewLifecycleOwner, {
            activity?.invalidateOptionsMenu()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_artlcie, menu)
        super.onCreateOptionsMenu(menu, inflater)
        menu.getItem(0).setIcon(if (viewModel.favBean.value == null) R.mipmap.ic_fav_white else R.mipmap.ic_fav)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_fav -> {
                viewModel.toggleFavStatus(args.url, args.title, args.image)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}