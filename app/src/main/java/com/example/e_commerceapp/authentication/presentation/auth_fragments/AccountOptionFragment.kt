package com.example.e_commerceapp.authentication.presentation.auth_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentAccountOptionsBinding

class AccountOptionFragment: Fragment(){
    private lateinit var binding: FragmentAccountOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountOptionsBinding.inflate(inflater)

        binding.buttonRegisterAccountOptions.setOnClickListener{
            findNavController().navigate(R.id.action_accountOptionFragment_to_registerFragment)
        }

        binding.buttonLoginAccountOptions.setOnClickListener{
            findNavController().navigate(R.id.action_accountOptionFragment_to_loginFragment)
        }

        return binding.root
    }
}