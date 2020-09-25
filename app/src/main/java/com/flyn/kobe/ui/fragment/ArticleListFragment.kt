package com.flyn.kobe.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flyn.kobe.R
import com.flyn.kobe.bean.CategoryData
import com.flyn.kobe.databinding.FragmentArticleListBinding
import com.flyn.kobe.ui.adapter.ArticleListAdapter
import com.flyn.kobe.ui.viewmodel.ArticleListViewModel

class ArticleListFragment(private val categoryData: CategoryData) : Fragment() {

    private lateinit var binding: FragmentArticleListBinding
    private lateinit var adapter: ArticleListAdapter
    private val viewModel by viewModels<ArticleListViewModel> { defaultViewModelProviderFactory }
    private var pageNum = 1
    private var loadMoreEnable = true
    private var isLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentArticleListBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initView()
        initData()
        setListener()
    }

    private fun initView() {
        binding.recycleView.layoutManager = LinearLayoutManager(activity)
        adapter = ArticleListAdapter()
        binding.recycleView.adapter = adapter

        binding.swiperefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
    }

    private fun initData() {
        isLoading = true
        binding.swiperefresh.post { binding.swiperefresh.isRefreshing = true }
        viewModel.getArticleData(pageNum, categoryData)
    }

    private fun setListener() {
        viewModel.articleData.observe(viewLifecycleOwner, {
            binding.swiperefresh.isRefreshing = false
            isLoading = false
            if (pageNum == 0) {
                adapter.data = ArrayList(it)
            } else {
                loadMoreEnable = it.size == 10
                adapter.data.addAll(it)
            }
            adapter.notifyDataSetChanged()
        })
        binding.swiperefresh.setOnRefreshListener {
            pageNum = 0
            viewModel.getArticleData(pageNum, categoryData)
        }
        binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastCompletelyVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                if (adapter.itemCount > 0 && lastCompletelyVisibleItemPosition >= adapter.itemCount - 1 && !isLoading && loadMoreEnable) {
                    pageNum++
                    isLoading = true
                    viewModel.getArticleData(pageNum, categoryData)
                }
            }
        })
    }


}