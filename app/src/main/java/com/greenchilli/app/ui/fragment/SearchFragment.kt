package com.greenchilli.app.ui.fragment

import SearchViewAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenchilli.app.R
import com.greenchilli.app.databinding.FragmentSearchBinding
import com.greenchilli.app.model.SearchViewResponse
import kotlinx.coroutines.NonDisposableHandle.parent


class SearchFragment : Fragment(){
    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = listOf<SearchViewResponse>(
            SearchViewResponse(R.drawable.menu2, "HotDog", "$17"),
            SearchViewResponse(R.drawable.menu2, "pizza", "$17"),
            SearchViewResponse(R.drawable.menu2, "Burger", "$17"),
            SearchViewResponse(R.drawable.menu2, "Momos", "$17"),
            SearchViewResponse(R.drawable.menu2, "Chai", "$17"),
            SearchViewResponse(R.drawable.menu2, "Dosa", "$17"),
            SearchViewResponse(R.drawable.menu2, "Chilli", "$17"),
            SearchViewResponse(R.drawable.menu2, "Coffee", "$17"),
            SearchViewResponse(R.drawable.menu2, "Pakoda", "$17"),
            SearchViewResponse(R.drawable.menu2, "Mirchie", "$17"),
            SearchViewResponse(R.drawable.menu2, "Green", "$17"),
            SearchViewResponse(R.drawable.menu4, "Vegiie", "$17"),
            SearchViewResponse(R.drawable.menu2, "SweetCorn", "$17"),
            SearchViewResponse(R.drawable.menu2, "Soup", "$17"),
            SearchViewResponse(R.drawable.menu2, "Paneer", "$17"),
            SearchViewResponse(R.drawable.menu2, "Chilli Paneer", "$17"),
            SearchViewResponse(R.drawable.menu2, "Grilled Paneer", "$17"),
            SearchViewResponse(R.drawable.menu2, "Cold Cofee", "$17"),
            SearchViewResponse(R.drawable.menu2, "Golgappe", "$17"),
            SearchViewResponse(R.drawable.menu2, "Chole", "$17"),
            SearchViewResponse(R.drawable.menu2, "Bhatoore", "$17"),
            SearchViewResponse(R.drawable.menu2, "Aloo Tikki", "$17"),
            SearchViewResponse(R.drawable.menu2, "Pawbhaji", "$17"),
            SearchViewResponse(R.drawable.menu2, "Kulhad Pizza", "$17"),
        )
        var adapter = SearchViewAdapter(requireContext(),item)
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.adapter = adapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?) : Boolean {
                if (query.isNullOrEmpty()) {
                    val adapter = SearchViewAdapter(requireContext(), item)
                    binding.searchRecyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                } else {
                    val list = ArrayList<SearchViewResponse>()
                    for (i in item) {
                        if (i.foodName.toLowerCase().contains(query.toLowerCase())) {
                            list.add(i)
                        }
                    }
                    val adapter = SearchViewAdapter(requireContext(), list)
                    binding.searchRecyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                return true
            }

            override fun onQueryTextChange(query: String?) : Boolean {
                if (query.isNullOrEmpty()) {
                    val adapter = SearchViewAdapter(requireContext(), item)
                    binding.searchRecyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                return true
            }
        })
    }

}

