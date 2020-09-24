package com.flyn.kobe.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.flyn.kobe.bean.CategoryData
import com.flyn.kobe.databinding.FragmentArticleListBinding
import com.flyn.kobe.ui.viewmodel.ArticleListViewModel

class ArticleListFragment(private val categoryData: CategoryData) : Fragment() {

    private lateinit var binding: FragmentArticleListBinding
    private val viewModel by viewModels<ArticleListViewModel> { defaultViewModelProviderFactory }
    private var pageNum = 0

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
    }

    private fun initData() {
        binding.swiperefresh.isRefreshing = true
        viewModel.getArticleData(pageNum, category = categoryData)
    }

    private fun setListener() {
        viewModel.articleData.observe(viewLifecycleOwner, {
        })
        binding.swiperefresh.setOnRefreshListener {
            pageNum = 0
            viewModel.getArticleData(pageNum, category = categoryData)
        }
    }


}