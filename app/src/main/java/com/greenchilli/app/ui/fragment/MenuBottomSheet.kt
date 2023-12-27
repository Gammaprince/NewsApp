package com.greenchilli.app.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.greenchilli.app.R
import com.greenchilli.app.data.MenuBottomSheetRepository
import com.greenchilli.app.databinding.MenuBottomSheetBinding
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.MenuItemResponse
import com.greenchilli.app.ui.adapter.MenuItemAdapter
import com.greenchilli.app.ui.viewmodel.HomeFragmentViewModel
import com.greenchilli.app.ui.viewmodel.MenuBottomSheetViewModel
import com.greenchilli.app.ui.viewmodel.factory.HomeFragmentViewModelFactory
import com.greenchilli.app.ui.viewmodel.factory.MenutBottomSheetViewModelFactory

class MenuBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : MenuBottomSheetBinding
    private lateinit var viewModel:MenuBottomSheetViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MenuBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = MenuBottomSheetRepository()
        viewModel = ViewModelProvider(requireActivity(),MenutBottomSheetViewModelFactory(repository)).get(MenuBottomSheetViewModel::class.java)
        viewModel.menuList.observe(this, Observer {
            when(it){
                is Resource.Success -> {
                    dismissProgressDialog()
                    val adapter = it.data?.let { it1 -> MenuItemAdapter(requireContext(), it1) }
                    binding.let {
                        it.menuRecyclerView.adapter = adapter
                        it.menuRecyclerView.layoutManager= LinearLayoutManager(requireContext())
                        adapter?.notifyDataSetChanged()
                    }
                }
                is Resource.Loading -> {
                    showCircularProgressDialog()
                }
                is Resource.Error -> {

                }
            }


        })
    }

    private fun showCircularProgressDialog() {
        progressDialog.setMessage("Loading...") // Set a message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER) // Use a circular style
        progressDialog.setCancelable(false) // Prevent user from canceling with back button
        progressDialog.show()
    }

    private fun dismissProgressDialog() {
        progressDialog.dismiss()
    }
}