package com.martin.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.core.AuthStates
import com.martin.core.SessionManager
import com.martin.core.db.auth.LoginRequestModel
import com.martin.core.db.auth.SignUpRequest
import com.martin.core.db.auth.TokenResponse
import com.martin.core.pref.PrefUtils
import com.martin.core.pref.Prefs
import com.martin.core.repository.AuthRepository
import com.martin.core.utils.extensions.launchSafeWithErrorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefUtils: PrefUtils,
    private val prefs: Prefs,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val toastMessage = Channel<String>()

    fun signUp(formData: SignUpRequest) {
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                val response = authRepository.signUp(formData, context)
                if (response.second != null) {
                    SessionManager.currentAuthState.value = AuthStates.AUTHORISED
                    response.second?.user?.let {
                        prefUtils.updateUser(it)
                    }
                    updateTokens(response.second?.accessToken, response.second?.refreshToken)
                }
            },
            onError = {
                toastMessage.trySend("Something went wrong")
            }
        )
    }

    fun login(formData: LoginRequestModel) {
        viewModelScope.launchSafeWithErrorHandling(
            block = {
                val response = authRepository.logIn(formData)
                if (response.second != null) {
                    SessionManager.currentAuthState.value = AuthStates.AUTHORISED
                    response.second?.user?.let {
                        prefUtils.updateUser(it)
                    }
                    updateTokens(response.second?.accessToken, response.second?.refreshToken)
                }
            },
            onError = {
                toastMessage.trySend("Something went wrong")
            }
        )
    }

    private fun updateTokens(accessToken: String?, refreshToken: String?) {
        val tokenResponse = TokenResponse(accessToken = accessToken, refreshToken = refreshToken)
        prefs.updateSecurityToken(tokenResponse)
    }
}