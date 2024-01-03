package com.example.e_commerceapp.authentication.presentation.auth_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.authentication.domain.util.RegisterValidatoins
import com.example.e_commerceapp.authentication.domain.util.Resource
import com.example.e_commerceapp.authentication.presentation.viewmodel.LoginViewModel
import com.example.e_commerceapp.authentication.presentation.viewmodel.RegisterViewModel
import com.example.e_commerceapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment: Fragment(){
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)

        binding.tvDontHaveAnAccount.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.apply {
            buttonLogin.setOnClickListener{
                val email = etLoginEmail.text.toString().trim()
                val password = etLoginPassword.text.toString().trim()

                viewModel.loginUser(email, password)
            }
        }


        lifecycleScope.launch {
            viewModel.loginFlow.collect{resources ->
                when(resources){
                    is Resource.Error -> {
                        Toast.makeText(context, "failed to login", Toast.LENGTH_SHORT).show()
                        binding.buttonLogin.revertAnimation()
                    }
                    is Resource.loading -> {
                        binding.buttonLogin.startAnimation()
                    }
                    is Resource.success -> {
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                        binding.buttonLogin.revertAnimation()
                    }
                    else ->{

                    }
                }
            }
        }


        return binding.root
    }
}