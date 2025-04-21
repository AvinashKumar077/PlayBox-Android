package com.martin.auth

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.core.db.SignUpRequest
import com.martin.core.repository.AuthRepository
import com.martin.core.utils.extensions.launchSafeWithErrorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    val toastMessage = Channel<String>()

     fun signUp(formData: SignUpRequest){
        viewModelScope.launchSafeWithErrorHandling (
            block = {
                authRepository.signUp(formData, context)
            },
            onError = {
                toastMessage.trySend("Something went wrong")
            }
        )
    }
}