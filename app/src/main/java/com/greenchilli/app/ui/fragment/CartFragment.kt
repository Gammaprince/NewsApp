package com.greenchilli.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greenchilli.app.R
import com.greenchilli.app.data.AddToCartRepository
import com.greenchilli.app.databinding.FragmentCartBinding
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.CartFragmentResponse
import com.greenchilli.app.ui.adapter.CartFragmentAdapter
import com.greenchilli.app.ui.viewmodel.CartFragmentViewModel
import com.greenchilli.app.ui.viewmodel.factory.CartFragmentViewModelFactory


class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var viewModel : CartFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val addToCartRepository = AddToCartRepository()
        viewModel = ViewModelProvider(requireActivity(),CartFragmentViewModelFactory(addToCartRepository)).get(CartFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_cartFragment_to_orderPlacement)
        }
        viewModel.cartList.observe(this, Observer {
            when(it){
                is Resource.Success -> {
                    val adapter = it.data?.let { it1 -> CartFragmentAdapter(requireContext(), it1 , viewModel) }
                    binding.cartRecyclerView.adapter = adapter
                    binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    adapter?.notifyDataSetChanged()
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
        })







    }
//    private fun showNewFragment() {
//        val myFragment = orderPlacement()
//        val transaction = (requireActivity())
//            .supportFragmentManager
//            .beginTransaction()
//        transaction.replace(R.id.fragmentContainerView,myFragment)
//        transaction.commit()
//    }

}