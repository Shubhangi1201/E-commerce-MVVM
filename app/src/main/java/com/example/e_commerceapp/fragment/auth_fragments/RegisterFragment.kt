package com.example.e_commerceapp.fragment.auth_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.e_commerceapp.data.User
import com.example.e_commerceapp.databinding.FragmentRegisterBinding
import com.example.e_commerceapp.util.Resource
import com.example.e_commerceapp.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        return binding.root
    }
}