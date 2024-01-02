package com.example.e_commerceapp.authentication.presentation.viewmodel

import androidx.lifecycle.ViewModel
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
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel(){

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser?>>(Resource.unspecified())
    val loginFlow : Flow<Resource<FirebaseUser?>> = _loginFlow

    private val _validation = Channel<RegisterFieldState>()
    val validation = _validation.receiveAsFlow()

    fun loginUser(email: String, password: String){
      if (CheckValidation(email, password)){
          runBlocking {
              _loginFlow.emit(Resource.loading())
          }

          firebaseAuth.signInWithEmailAndPassword(email, password)
              .addOnSuccessListener {
                  it.user.let {
                      _loginFlow.value = Resource.success(it)
                  }
              }.addOnFailureListener{
                  _loginFlow.value = Resource.Error(it.message.toString())
              }
      }else{
          val loginFieldState = RegisterFieldState(
              validateEmail(email), validatePassword(password)
          )

          runBlocking {
              _validation.send(loginFieldState)
          }
      }
    }


    private fun CheckValidation(email: String, password: String): Boolean{
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(password)
        val shouldLogin = emailValidation is RegisterValidatoins.Success
                && passwordValidation is RegisterValidatoins.Success

        return shouldLogin
    }
}