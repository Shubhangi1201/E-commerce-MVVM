package com.example.e_commerceapp.util

sealed class RegisterValidatoins{
    object Success: RegisterValidatoins()
    data class Failed(val message: String): RegisterValidatoins()


}


data class RegisterFieldState(
    val email:  RegisterValidatoins,
    val password: RegisterValidatoins
)