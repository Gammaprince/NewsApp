package com.greenchilli.app.ui.fragment

import SearchViewAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenchilli.app.R
import com.greenchilli.app.data.AddToCartRepository
import com.greenchilli.app.data.SearchRepository
import com.greenchilli.app.databinding.FragmentSearchBinding
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.FamousFoodResponse
import com.greenchilli.app.ui.viewmodel.SearchViewModel
import com.greenchilli.app.ui.viewmodel.factory.SearchViewModelFactory
class SearchFragment : Fragment(){
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel : SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val searchRepository = SearchRepository()
        val addToCartRepository = AddToCartRepository()
        viewModel = ViewModelProvider(requireActivity(), SearchViewModelFactory(searchRepository,addToCartRepository)).get(
            SearchViewModel::class.java)
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
//        viewModel.setItem()
        viewModel.listOfItems.observe(this, Observer {
            when(it){
                is Resource.Success -> {
                    var adapter = it.data?.let { it1 -> SearchViewAdapter(requireContext(), it1 , viewModel) }
                    binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.searchRecyclerView.adapter = adapter
                    adapter?.notifyDataSetChanged()
                    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?) : Boolean {
                            if (query.isNullOrEmpty()) {
                                val adapter =
                                    it.data?.let { it1 -> SearchViewAdapter(requireContext(), it1 , viewModel) }
                                binding.searchRecyclerView.adapter = adapter
                                adapter?.notifyDataSetChanged()
                            } else {
                                val list = ArrayList<FamousFoodResponse>()
                                for (i in it.data!!) {
                                    if (i.foodName.toLowerCase().contains(query.toLowerCase())) {
                                        list.add(i)
                                    }
                                }
                                val adapter = SearchViewAdapter(requireContext(), list , viewModel)
                                binding.searchRecyclerView.adapter = adapter
                                adapter?.notifyDataSetChanged()
                            }
                            return true
                        }
                        override fun onQueryTextChange(query: String?) : Boolean {
                            if (query.isNullOrEmpty()) {
                                val adapter =
                                    it.data?.let { it1 -> SearchViewAdapter(requireContext(), it1 , viewModel) }
                                binding.searchRecyclerView.adapter = adapter
                                adapter?.notifyDataSetChanged()
                            }
                            return true
                        }
                    })
                }

                is Resource.Loading -> {
                }

                is Resource.Error -> {
                    Toast.makeText(requireActivity(),"Something went wrong | Please Check your Internet Connection",Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

}

