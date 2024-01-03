package com.example.e_commerceapp.authentication.presentation.auth_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.authentication.domain.model.User
import com.example.e_commerceapp.databinding.FragmentRegisterBinding
import com.example.e_commerceapp.authentication.domain.util.RegisterValidatoins
import com.example.e_commerceapp.authentication.domain.util.Resource
import com.example.e_commerceapp.authentication.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)

        binding.tvAlreadyAnAccount.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.apply {
            buttonRegister.setOnClickListener{
                val user = User(
                    etRegisterFirstName.text.toString().trim(),
                    etRegisterLastName.text.toString().trim(),
                    etRegisterEmail.text.toString().trim()
                )

                val password =  etRegisterPassword.text.toString()

                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }

        lifecycleScope.launch {
            viewModel.register.collect{
                when(it){
                    is Resource.Error -> {
                      Log.e("test", it.message.toString())
                        binding.buttonRegister.revertAnimation()
                    }
                    is Resource.loading -> {
                        binding.buttonRegister.startAnimation()
                    }
                    is Resource.success -> {
                        Log.d("test", it.data.toString())
                        binding.buttonRegister.revertAnimation()

                    }

                    else -> {

                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.validation.collect{ validation ->
                if(validation.email is RegisterValidatoins.Failed){
                    withContext(Dispatchers.Main){
                        binding.etRegisterEmail.apply {
                            requestFocus()
                            error = validation.email.message
                        }

                    }
            }
                if(validation.password is RegisterValidatoins.Failed)
                    withContext(Dispatchers.Main){
                        binding.etRegisterPassword.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
            }


        }
        return binding.root
    }
}