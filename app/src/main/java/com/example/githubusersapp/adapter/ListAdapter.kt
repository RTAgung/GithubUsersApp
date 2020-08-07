package com.example.githubusersapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersapp.R
import com.example.githubusersapp.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.DetailViewHolder>() {
    private val mData = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: User) {
            with(itemView) {
                tv_user_login.text = data.login
                Picasso.get()
                    .load(data.avatar_url)
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_person_24)
                    .into(iv_user_avatar)
                this.setOnClickListener { onItemClickCallback?.onItemClicked(data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return DetailViewHolder(view)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}