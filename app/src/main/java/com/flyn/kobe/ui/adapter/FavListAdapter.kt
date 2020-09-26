package com.flyn.kobe.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.flyn.kobe.BR
import com.flyn.kobe.bean.FavBean
import com.flyn.kobe.databinding.LayoutArticleItemBinding
import com.flyn.kobe.databinding.LayoutFavItemBinding
import com.flyn.kobe.utils.ScreenUtil


class FavListAdapter : RecyclerView.Adapter<FavListAdapter.ViewHolder>() {

    var data = ArrayList<FavBean>()
    lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutFavItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding: LayoutFavItemBinding? = DataBindingUtil.getBinding(holder.itemView)

        binding?.setVariable(BR.favBean, data[position])
        binding?.executePendingBindings()
        (binding?.root?.layoutParams as RecyclerView.LayoutParams).bottomMargin = ScreenUtil.dip2px(holder.itemView.context, if (data.size - 1 == position) 24f else 0f)

        binding.root.setOnClickListener {
            onItemClickListener.onItemClick(holder.itemView, position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}