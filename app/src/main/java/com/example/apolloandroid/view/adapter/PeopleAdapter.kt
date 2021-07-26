package com.example.apolloandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apolloandroid.databinding.ListItemBinding
import com.example.starwars.PeopleListQuery

class PeopleAdapter (private val dataset: List<PeopleListQuery.Edge>) :
        RecyclerView.Adapter<PeopleViewHolder>() {

    var onEndOfListReached: (() -> Unit)? = null
    var onItemClicked: ((PeopleListQuery.Edge) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val binding = ListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return PeopleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val item = dataset[position]
        holder.personTextView.text = item.node?.name

        val description = item.node?.species?.name ?: "Human " + "from " + item.node?.homeworld?.name
        holder.specieTextView.text = description

        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(item)
        }

        if (position == dataset.size - 1) {
            onEndOfListReached?.invoke()
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}

class PeopleViewHolder (
    val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    val personTextView: TextView = binding.namePerson
    val specieTextView: TextView = binding.specieText


}