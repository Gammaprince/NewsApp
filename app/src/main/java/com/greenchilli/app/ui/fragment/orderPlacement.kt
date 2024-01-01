package com.greenchilli.app.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.greenchilli.app.R
import com.greenchilli.app.data.OrderPlacementRepository
import com.greenchilli.app.databinding.FragmentOrderPlacementBinding
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.UserInfoResponse
import com.greenchilli.app.ui.viewmodel.CartFragmentViewModel
import com.greenchilli.app.ui.viewmodel.OrderPlacementViewModel
import com.greenchilli.app.ui.viewmodel.factory.OrderPlacementViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class orderPlacement : Fragment() {
    private lateinit var binding: FragmentOrderPlacementBinding
    private lateinit var viewModel: OrderPlacementViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orderPlacementRepository = OrderPlacementRepository(requireContext());
        viewModel = ViewModelProvider(
            requireActivity(),
            OrderPlacementViewModelFactory(orderPlacementRepository)
        ).get(OrderPlacementViewModel::class.java);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderPlacementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.setInfo();
        val navController = Navigation.findNavController(view)
        binding.submitOrder.setOnClickListener {
            val result = lifecycleScope.launch(Dispatchers.IO) {
                val it = lifecycleScope.async {
                    viewModel.submitOrder()
                }
                withContext(Dispatchers.Main) {
                    when (it.await()) {
                        is Resource.Success<*> -> {

                            Toast.makeText(
                                requireContext(),
                                "Your Order Has Been Placed",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                            navController.navigate(
                                R.id.action_orderPlacement_to_homeFragment, null,
                                NavOptions.Builder()
                                    .setPopUpTo(
                                        R.id.homeFragment,
                                        true
                                    ) // Pop up to previousFragment
                                    .build()
                            )


                        }
                        is Resource.Loading<*> -> {

                        }
                        is Resource.Error<*> -> {
                            Toast.makeText(
                                requireActivity(),
                                "Please Add something into cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        viewModel.getInfo.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    val data: UserInfoResponse? = it.data
                    binding.name.setText(data?.name)
                    binding.address.setText(data?.address)
                    binding.phone.setText(data?.phone)
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        })
        viewModel.getTotalPrice.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.price.setText(it.data.toString())
                    Log.d("Paya", "Success");
                }
                is Resource.Error -> {
                    Log.d("Paya", "Error")
                }
                is Resource.Loading -> {
                    Log.d("Paya", " start")
                }
            }
        })
    }
}