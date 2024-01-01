package com.greenchilli.app.ui.fragment

import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.R
import com.greenchilli.app.data.OrderPlacementRepository
import com.greenchilli.app.databinding.FragmentProfileBinding
import com.greenchilli.app.ui.viewmodel.ProfileFragmentViewModel
import com.greenchilli.app.ui.viewmodel.factory.ProfileFragmentViewModelFactory


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orderPlacementRepository = OrderPlacementRepository(requireActivity())
        viewModel = ViewModelProvider(requireActivity(),ProfileFragmentViewModelFactory(orderPlacementRepository)).get(ProfileFragmentViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getInfo.observe(this, Observer {
            when(it){
                is Resource.Success -> {
                    val data = it.data
                    binding.name.setText(data?.name)
                    binding.address.setText(data?.address)
                    binding.email.setText(data?.emial)
                    binding.phone.setText(data?.phone)
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
        })
    }
}