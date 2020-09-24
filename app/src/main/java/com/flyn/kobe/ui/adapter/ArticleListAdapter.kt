package com.flyn.kobe.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.flyn.kobe.BR
import com.flyn.kobe.bean.ArticleData
import com.flyn.kobe.databinding.LayoutArticleItemBinding
import com.flyn.kobe.utils.ScreenUtil


class ArticleListAdapter : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    var data = ArrayList<ArticleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding: LayoutArticleItemBinding? = DataBindingUtil.getBinding(holder.itemView)
        binding?.setVariable(BR.article, data[position])
        binding?.executePendingBindings()

        (binding?.root?.layoutParams as RecyclerView.LayoutParams).bottomMargin = ScreenUtil.dip2px(holder.itemView.context, if (data.size - 1 == position) 24f else 0f)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}