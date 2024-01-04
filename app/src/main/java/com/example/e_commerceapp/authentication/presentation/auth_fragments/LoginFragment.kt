package com.example.e_commerceapp.authentication.presentation.auth_fragments

import android.content.Intent
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
import com.example.e_commerceapp.shopping.ShoppingActivity
import com.example.e_commerceapp.authentication.domain.util.Resource
import com.example.e_commerceapp.authentication.presentation.dialog.setupBottomSheetDialog
import com.example.e_commerceapp.authentication.presentation.viewmodel.LoginViewModel
import com.example.e_commerceapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        binding.tvForgetPassword.setOnClickListener{
            setupBottomSheetDialog { email ->
                viewModel.resetPassword(email)
            }

        }


        lifecycleScope.launch {
            viewModel.resetPassword.collect{resources ->
                when(resources){
                    is Resource.Error -> {
                        Snackbar.make(requireView(), "Error : ${resources.message}", Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.loading -> {
                        Snackbar.make(requireView(), "i am loading", Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.success -> {
                        Snackbar.make(requireView(), "Password reset link has been sent to your email", Snackbar.LENGTH_LONG).show()
                    }
                    else ->{

                    }
                }
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
                        Intent(requireActivity(), ShoppingActivity::class.java).also { intent->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    else ->{

                    }
                }
            }
        }


        return binding.root
    }
}