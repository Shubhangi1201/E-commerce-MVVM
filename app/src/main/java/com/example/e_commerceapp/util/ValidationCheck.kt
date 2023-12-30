package com.example.e_commerceapp.util

import android.util.Patterns

fun validateEmail(email: String): RegisterValidatoins{
    if(email.isEmpty())
        return RegisterValidatoins.Failed("email cannot be empty")

    if(!Patterns.EMAIL_ADDRESS.equals(email))
        return RegisterValidatoins.Failed("Wrong email format")

    return RegisterValidatoins.Success
}

fun validatePassword(password: String): RegisterValidatoins{

    if(password.isEmpty())
        return RegisterValidatoins.Failed("email cannot be empty")

    if(password.length < 6 ){
        return RegisterValidatoins.Failed("Password must contain 6 characters")
    }

    return RegisterValidatoins.Success
}