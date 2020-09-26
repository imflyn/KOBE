package com.flyn.kobe.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flyn.kobe.databinding.FragmentFavBinding
import com.flyn.kobe.ui.activity.HostActivity
import com.flyn.kobe.ui.adapter.FavListAdapter
import com.flyn.kobe.ui.viewmodel.FavViewModel
import com.flyn.kobe.utils.Util
import kotlinx.android.synthetic.main.fragment_main.*

class FavFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    private lateinit var adapter: FavListAdapter
    private val viewModel by viewModels<FavViewModel> { defaultViewModelProviderFactory }
    private var pageNum = 0
    private var loadMoreEnable = true
    private var isLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        setListener()
    }

    private fun initView() {
        binding.recycleView.layoutManager = LinearLayoutManager(activity)
        adapter = FavListAdapter()
        binding.recycleView.adapter = adapter

        toolbar.title = "收藏"
        (toolbar.layoutParams as LinearLayout.LayoutParams).topMargin = Util.getStatusBarHeight()

        val hostActivity = activity as HostActivity
        hostActivity.setSupportActionBar(toolbar)

        val navController = hostActivity.getNavController()
        val appBarConfiguration = hostActivity.getAppBarConfiguration()
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initData() {
        isLoading = true
        pageNum = 0
        viewModel.getFavData(pageNum)
    }


    private fun setListener() {
        viewModel.favBeanList.observe(viewLifecycleOwner, {
            isLoading = false
            if (pageNum == 0) {
                adapter.data = ArrayList(it)
            } else {
                loadMoreEnable = it.size == 10
                adapter.data.addAll(it)
            }
            adapter.notifyDataSetChanged()
        })
        binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastCompletelyVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                if (adapter.itemCount > 0 && lastCompletelyVisibleItemPosition >= adapter.itemCount - 1 && !isLoading && loadMoreEnable) {
                    pageNum++
                    isLoading = true
                    viewModel.getFavData(pageNum)
                }
            }
        })


        adapter.onItemClickListener = object : FavListAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val favBean = adapter.data[position]
                val action = FavFragmentDirections.actionFavFragmentToArticleFragment(favBean.url, favBean.title, favBean.image)
                view?.findNavController()?.navigate(action)
            }
        }
    }
}