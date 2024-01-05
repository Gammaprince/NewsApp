package com.greenchilli.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.R
import com.greenchilli.app.data.OrderPlacementRepository
import com.greenchilli.app.databinding.FragmentProfileBinding
import com.greenchilli.app.ui.activity.LoginActivity
import com.greenchilli.app.ui.viewmodel.ProfileFragmentViewModel
import com.greenchilli.app.ui.viewmodel.factory.ProfileFragmentViewModelFactory


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileFragmentViewModel
    private val client2 = "110599695437-mv2c6pn2h3acd7rksvt4j46pqf42m6hk.apps.googleusercontent.com"

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
        binding.save.setOnClickListener {
            viewModel.setInfo(binding.name.text.toString() , binding.address.text.toString() , binding.phone.text.toString() , binding.email.text.toString())
        }
        binding.signout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
                client2
            ).requestEmail().build()
            GoogleSignIn.getClient(requireActivity(),gso).signOut()
            startActivity(Intent(requireActivity(),LoginActivity::class.java))
        }
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