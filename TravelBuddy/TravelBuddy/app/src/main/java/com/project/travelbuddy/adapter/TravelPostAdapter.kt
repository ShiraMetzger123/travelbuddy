package com.project.travelbuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.travelbuddy.R
import com.project.travelbuddy.model.TravelPost
import com.squareup.picasso.Picasso

class TravelPostAdapter(private val posts: List<TravelPost>,
                        private val onPostClick: (String) -> Unit ) :
    RecyclerView.Adapter<TravelPostAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val description: TextView = itemView.findViewById(R.id.tvDescription)
        private val location: TextView = itemView.findViewById(R.id.tvLocation)
        private val image: ImageView = itemView.findViewById(R.id.ivPostImage)

        fun bind(post: TravelPost) {
            title.text = post.title
            description.text = post.description
            location.text = post.location

            // Load Image with Picasso
            if (post.imageUrl.isNotEmpty()) {
                Picasso.get().load(post.imageUrl).into(image)
            } else {
                image.setImageResource(R.drawable.splash_image) // Fallback image
            }
            itemView.setOnClickListener {
                onPostClick(post.id)  // Pass post ID to navigate
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_travel_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size
}

