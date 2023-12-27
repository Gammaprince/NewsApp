package com.greenchilli.app.ui.fragment

import PreviouslyBuyAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenchilli.app.R
import com.greenchilli.app.databinding.FragmentHistoryBinding
import com.greenchilli.app.model.PreviousBuyResponse

class HistoryFragment : Fragment() {
   private lateinit var binding : FragmentHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = listOf<PreviousBuyResponse>(
            PreviousBuyResponse(R.drawable.menu2,"Momos","$13"),
            PreviousBuyResponse(R.drawable.menu2,"Pizza","$13"),
            PreviousBuyResponse(R.drawable.menu2,"Burger","$13"),
            PreviousBuyResponse(R.drawable.menu2,"Pakoda","$13"),
            PreviousBuyResponse(R.drawable.menu2,"GolGappe","$13"),
        )
        val adapter = PreviouslyBuyAdapter(requireContext(),item)
        binding.let {
            it.previousRecyclerView.adapter = adapter
            it.previousRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}