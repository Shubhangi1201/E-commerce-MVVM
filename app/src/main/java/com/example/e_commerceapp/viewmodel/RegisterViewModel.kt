package com.example.e_commerceapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.e_commerceapp.data.User
import com.example.e_commerceapp.util.RegisterFieldState
import com.example.e_commerceapp.util.RegisterValidatoins
import com.example.e_commerceapp.util.Resource
import com.example.e_commerceapp.util.validateEmail
import com.example.e_commerceapp.util.validatePassword
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
                passwordValidation is RegisterValidatoins.Failed

        return shouldRegister
    }

}