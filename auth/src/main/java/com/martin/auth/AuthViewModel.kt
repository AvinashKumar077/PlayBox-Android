package com.martin.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.core.db.SignUpRequest
import com.martin.core.repository.AuthRepository
import com.martin.core.utils.extensions.launchSafeWithErrorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    suspend fun signUp(formData: SignUpRequest){
        viewModelScope.launchSafeWithErrorHandling (
            block = {
                authRepository.signUp(formData, context)
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}