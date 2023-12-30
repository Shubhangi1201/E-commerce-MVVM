package com.example.e_commerceapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.e_commerceapp.data.User
import com.example.e_commerceapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val _register = MutableStateFlow<Resource<FirebaseUser?>>(Resource.unspecified())
    val register : Flow<Resource<FirebaseUser?>> = _register



    fun createAccountWithEmailAndPassword(user: User, password: String){
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
    }

}