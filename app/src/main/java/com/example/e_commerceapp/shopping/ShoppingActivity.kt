package com.example.e_commerceapp.shopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityShoppingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.ShoppingHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}