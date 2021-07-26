package com.example.apolloandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apolloandroid.databinding.ListVehicleItemBinding
import com.example.starwars.DetailPersonQuery

class PersonDetailAdapter (
    private val dataset: List<DetailPersonQuery.Vehicle>
        ): RecyclerView.Adapter<PersonDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonDetailViewHolder {
        val binding = ListVehicleItemBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent
            ,false)

        return PersonDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonDetailViewHolder, position: Int) {
        val item = dataset[position]
        holder.vehicleText.text = item.name
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}

class PersonDetailViewHolder(
    val binding: ListVehicleItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        val vehicleText: TextView = binding.vehicle
}