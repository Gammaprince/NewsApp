package com.greenchilli.app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greenchilli.app.R
import com.greenchilli.app.databinding.FamousFoodLayoutBinding
import com.greenchilli.app.databinding.MenuItemBinding
import com.greenchilli.app.model.MenuItemResponse

class MenuItemAdapter(private val context: Context, private val response: List<MenuItemResponse>) :
    RecyclerView.Adapter<MenuItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FamousFoodLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return response.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = response[position]
        holder.bind(data.foodImage,data.foodName,data.foodPrice)
    }

    inner class ViewHolder(private val binding : FamousFoodLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(foodImage : String , foodName : String , foodPrice : String , text : String = "Add to Cart"){
            binding.let {
                it.foodName.text = foodName
                Glide.with(context).load(foodImage).into(it.foodImage);
                it.foodPrice.text = foodPrice
                it.addToCart.text = text
            }
        }
    }

}