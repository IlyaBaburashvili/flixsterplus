package com.example.flix

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val ACTOR_EXTRA = "ACTOR_EXTRA"
private const val TAG = "ActorAdapter"

class ActorAdapter (private val context: Context,
                     private val people: List<Actor>,
) :
    RecyclerView.Adapter<ActorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.person_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = people[position]
        holder.bind(holder, person)
    }

    override fun getItemCount() = people.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val nameTV = itemView.findViewById<TextView>(R.id.actorName)
        private val actorPhoto = itemView.findViewById<ImageView>(R.id.actorImage)
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(holder: ViewHolder,p: Actor) {
            nameTV.text = p.name
            Glide.with(holder.itemView)
                .load(p.profileImageUrl)
                .centerInside()
                .into(actorPhoto)
        }

        override fun onClick(v: View?) {
            val person = people[absoluteAdapterPosition]
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ACTOR_EXTRA, person)
            context.startActivity(intent)
        }
    }
}