package com.greenchilli.app.ui.fragment

import PreviouslyBuyAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.greenchilli.app.R
import com.greenchilli.app.data.HistoryRepository
import com.greenchilli.app.databinding.FragmentHistoryBinding
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.PreviousBuyResponse
import com.greenchilli.app.ui.viewmodel.HistoryFragmentViewModel
import com.greenchilli.app.ui.viewmodel.factory.HistoryFragmentViewModelFactory

class HistoryFragment : Fragment() {
   private lateinit var binding : FragmentHistoryBinding
   private lateinit var viewModel : HistoryFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this,HistoryFragmentViewModelFactory(HistoryRepository())).get(HistoryFragmentViewModel::class.java)
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val item = listOf<PreviousBuyResponse>(
//            PreviousBuyResponse(R.drawable.menu2,"Momos","$13"),
//            PreviousBuyResponse(R.drawable.menu2,"Pizza","$13"),
//            PreviousBuyResponse(R.drawable.menu2,"Burger","$13"),
//            PreviousBuyResponse(R.drawable.menu2,"Pakoda","$13"),
//            PreviousBuyResponse(R.drawable.menu2,"GolGappe","$13"),
//        )
        viewModel.getItem.observe(this, Observer {
            when(it){
                is Resource.Success -> {
                    val adapter = PreviouslyBuyAdapter(requireContext(),it.data)


                    if(it?.data?.size != 0){
                        val recent = it.data?.get(it.data.size-1)
                        if (recent != null) {
                            setRecentPurchased(recent.foodImage , recent.foodName , recent.price)
                        }
                    }

                    binding.let {
                        it.previousRecyclerView.adapter = adapter
                        it.previousRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    }
                    adapter?.notifyDataSetChanged()
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    Toast.makeText(requireActivity(),"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
    fun setRecentPurchased(foodImage : String , foodName : String , foodPrice : String){
        Glide.with(requireActivity()).load(foodImage ?: "").into(binding.recentPurchasedFoodImage)
        binding.recentPurchasedFoodName.text = foodName
        binding.recentPurchasedFoodPrice.text = foodPrice
    }
}