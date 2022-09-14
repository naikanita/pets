package com.learning.pets.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.pets.databinding.PetsRowBinding
import com.learning.pets.model.Pet

/**
 * @author Anita
 * This adapter is used to setup pet list data to recycler view
 */
class PetAdapter(
    private val itemClickListener: ItemClickListener,
    private val petList: List<Pet?>?
) : RecyclerView.Adapter<PetDataHolder>() {

    private lateinit var binding: PetsRowBinding

    interface ItemClickListener {
        fun onItemClick(url: String, view: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetDataHolder {
        binding = PetsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetDataHolder(binding)
    }

    override fun onBindViewHolder(holder: PetDataHolder, position: Int) {
        holder.bind(petList?.get(position))
        holder.itemView.setOnClickListener { view ->
            itemClickListener.onItemClick(petList?.get(position)!!.contentUrl, view)
        }
    }

    override fun getItemCount(): Int = petList!!.size
}

/**
 * This class use to hold Pets object data
 */
class PetDataHolder(
    private val binding: PetsRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pet: Pet?) {
        binding.pet = pet
    }
}