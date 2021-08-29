package com.odroid.naviproject.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.odroid.naviproject.R
import com.odroid.naviproject.model.PullRequest

class PullRequestAdapter(
    private val pullRequests: ArrayList<PullRequest>
    ) :
    RecyclerView.Adapter<PullRequestAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val prIconView = itemView.findViewById<ImageView>(R.id.iv_pr_icon)
        private val prTitle = itemView.findViewById<TextView>(R.id.tv_pr_title)
        private val prOpenedDesc = itemView.findViewById<TextView>(R.id.tv_pr_opened_desc)
        private val prClosedDesc = itemView.findViewById<TextView>(R.id.tv_pr_closed_desc)

        fun bindView(item: PullRequest) {
            prTitle.text = item.title
            prOpenedDesc.text = String.format(itemView.context.getString(R.string.label_opened_pr_desc,
                item.user?.userName, item.createdAt))
            prClosedDesc.text = String.format(itemView.context.getString(R.string.label_closed_pr_desc,
                item.closedAt))

            Glide.with(itemView.context)
                .load(item.user?.photoUrl)
                .into(prIconView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val pullRequestView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_pull_request,
            parent, false
        )
        return ViewHolder(pullRequestView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pullRequest = pullRequests[position]
        holder.bindView(pullRequest)
    }

    override fun getItemCount(): Int {
        return pullRequests.size
    }

    fun addData(list: List<PullRequest>) {
        pullRequests.addAll(list)
    }
}