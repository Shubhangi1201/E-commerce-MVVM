package com.example.e_commerceapp.authentication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel(){

    private val _loginFlow = MutableSharedFlow<Resource<FirebaseUser?>>()
    val loginFlow  = _loginFlow.asSharedFlow()

    private val _resetPassword = MutableSharedFlow<Resource<String>>()
    val resetPassword = _resetPassword.asSharedFlow()


    fun loginUser(email: String, password: String) {
        runBlocking {
            _loginFlow.emit(Resource.loading())
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                viewModelScope.launch {
                    it.user.let {
                        _loginFlow.emit(Resource.success(it))
                    }
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _loginFlow.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun resetPassword(email: String){
       viewModelScope.launch {
           _resetPassword.emit(Resource.loading())
       }
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                viewModelScope.launch {

                _resetPassword.emit(Resource.success(email))
                }
            }.addOnFailureListener{
                viewModelScope.launch {
                    _resetPassword.emit(Resource.Error(it.message.toString()))
                }
            }
    }



}