package com.greenchilli.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.greenchilli.app.R
import com.greenchilli.app.databinding.FragmentItemDetailBinding

private const val ARG_PARAM2 = "param2"

class ItemDetail : Fragment() {
    private lateinit var binding : FragmentItemDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemDetailBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val foodLink = this.arguments?.getString("link")
        val description = this.arguments?.getString("description")
        val ingredient = this.arguments?.getString("ingredient")
        Glide.with(requireActivity()).load(foodLink).into(binding.foodImage)
        binding.addToCart.setOnClickListener {
//            viewModel.addToCart()
        }
        binding.description.text = description
        binding.ingredient.text = ingredient
        binding.back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
}