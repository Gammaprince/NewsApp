package com.greenchilli.app.ui.fragment

import HomeFragmentAdapter
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.FirebaseApp
import com.greenchilli.app.data.AddToCartRepository
import com.greenchilli.app.data.FamousFoodRepository
import com.greenchilli.app.data.MenuBottomSheetRepository
import com.greenchilli.app.data.OffersRepository
import com.greenchilli.app.databinding.AlertDialogueBinding
import com.greenchilli.app.databinding.FragmentHomeBinding
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.ui.viewmodel.HomeFragmentViewModel
import com.greenchilli.app.ui.viewmodel.factory.HomeFragmentViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var homeViewModel: HomeFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(requireContext())
        val repository  = FamousFoodRepository()
        val offerRepo = OffersRepository()
        val addToCartRepository = AddToCartRepository()
        homeViewModel = ViewModelProvider(requireActivity(),HomeFragmentViewModelFactory(repository , offerRepo , addToCartRepository)).get(HomeFragmentViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        FirebaseApp.initializeApp(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        homeViewModel.setFamousFoodItem()
//        homeViewModel.setOffers("https://firebasestorage.googleapis.com/v0/b/anor-e2bd5.appspot.com/o/banner1.jpg?alt=media&token=76c8008a-60e9-4de4-936a-fc61f399e174")
        homeViewModel.famousFoodList.observe(this, Observer {
            when(it){
                is Resource.Loading -> {
                    showCircularProgressDialog()
                }
                is Resource.Success -> {
                    dismissProgressDialog()

                    val adapter = it.data?.let { it1 -> HomeFragmentAdapter(requireContext(), it1 , homeViewModel) }
                    binding.famousFoodRV.adapter = adapter
                    binding.famousFoodRV.layoutManager = LinearLayoutManager(requireContext())
                    adapter?.notifyDataSetChanged()
                }
                is Resource.Error -> {

                }
            }

        })
        homeViewModel.offerLIst.observe(this, Observer {
            val imageList = ArrayList<SlideModel>()
            for(image in it){
                imageList.add(SlideModel(image))
            }
            val imageSlider = binding.imageSlider
            imageSlider.setImageList(imageList);
            imageSlider.setImageList(imageList, ScaleTypes.FIT)
            imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
        })


        binding.viewMenu.setOnClickListener {
            val menuBottomSheet = MenuBottomSheet();
            menuBottomSheet.show(parentFragmentManager,"S")
        }
//        showCustomDialog()

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
    private fun showCustomDialog() {

        // Customize your dialog here (e.g., set title, button click listeners)
        val dialogView = AlertDialogueBinding.inflate(layoutInflater,null)
        dialogView.alertMessage.text = "Please Check Connection"

        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setView(dialogView.root)
        val alertDialog = alertDialogBuilder.create()

        dialogView.ok.setOnClickListener{
            // Handle positive button click
            // Do something with enteredText

            alertDialog.dismiss()
        }

        dialogView.cancel.setOnClickListener {
            // Handle negative button click
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

}