package com.example.e_commerceapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.e_commerceapp.R
import com.example.e_commerceapp.authentication.presentation.auth_fragments.LoginFragment
import com.example.e_commerceapp.authentication.presentation.auth_fragments.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

    }
}