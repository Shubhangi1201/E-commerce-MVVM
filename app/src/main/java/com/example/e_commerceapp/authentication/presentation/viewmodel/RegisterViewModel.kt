package com.example.e_commerceapp.authentication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.e_commerceapp.authentication.domain.model.User
import com.example.e_commerceapp.authentication.domain.util.RegisterFieldState
import com.example.e_commerceapp.authentication.domain.util.RegisterValidatoins
import com.example.e_commerceapp.authentication.domain.util.Resource
import com.example.e_commerceapp.authentication.domain.util.validateEmail
import com.example.e_commerceapp.authentication.domain.util.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val _register = MutableStateFlow<Resource<FirebaseUser?>>(Resource.unspecified())
    val register : Flow<Resource<FirebaseUser?>> = _register
    private val _validation = Channel<RegisterFieldState> ()
    val validation = _validation.receiveAsFlow()



    fun createAccountWithEmailAndPassword(user: User, password: String){
        if(CheckValidation(user, password)){
            runBlocking {
                _register.emit(Resource.loading())
            }
            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    it.user.let {
                        _register.value = Resource.success(it)
                    }
                }.addOnFailureListener{
                    _register.value = Resource.Error(it.message.toString())
                }
        }else{
            Log.d("test", "else part executed")
            val registerFieldState= RegisterFieldState(
            validateEmail(user.email), validatePassword(password)
            )
            runBlocking {
                _validation.send(registerFieldState)
            }
        }


    }

    private fun CheckValidation(user: User, password: String) : Boolean{
        val emailValidatoins = validateEmail(user.email)
        val passwordValidation = validatePassword(password)
        val shouldRegister = emailValidatoins is RegisterValidatoins.Success &&
                passwordValidation is RegisterValidatoins.Success

        return shouldRegister
    }

}