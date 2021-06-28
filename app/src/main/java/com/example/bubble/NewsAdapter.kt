package com.example.bubble

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val listener: NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {

    private val items = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_items, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currItems = items[position]
        holder.titleView.text = currItems.title
        holder.authorView.text = currItems.author
        Glide.with(holder.itemView.context).load(currItems.imgUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(newsItems: ArrayList<News>) {
        items.clear()
        items.addAll(newsItems)
        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val imageView: ImageView = itemView.findViewById(R.id.image)
    val authorView: TextView = itemView.findViewById(R.id.author)
}

fun interface NewsItemClicked {
    fun onItemClicked(item: News)
}