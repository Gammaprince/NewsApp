package com.greenchilli.app.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greenchilli.app.databinding.CartLayoutBinding
import com.greenchilli.app.ui.fragment.CartFragment
import com.greenchilli.app.model.CartFragmentResponse
import com.greenchilli.app.ui.viewmodel.CartFragmentViewModel

class CartFragmentAdapter(
    private val context: Context, private val response: List<CartFragmentResponse> , private val viewModel:CartFragmentViewModel
) : RecyclerView.Adapter<CartFragmentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CartLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return response.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = response[position]
        holder.delete.setOnClickListener {
            viewModel.deleteCart(data.foodName)
        }
        holder.minus.setOnClickListener{
            viewModel.decreaseCartCount(data.foodName)
        }
        holder.plus.setOnClickListener {
            viewModel.addToCart(data.foodImage,data.foodName,data.price)
        }
        holder.bind(data.foodImage, data.foodName, data.price, data.count)
    }

    inner class ViewHolder(val binding: CartLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val delete: ImageButton = binding.delete
        val minus = binding.minus
        val plus = binding.plus
        fun bind(foodImage: String, foodName: String, foodPrice: String, count: Int) {
            binding.foodName.text = foodName
            Glide.with(context).load(foodImage).into(binding.foodImage);
            binding.foodPrice.text = foodPrice
            binding.count.text = count.toString()
        }
    }
}