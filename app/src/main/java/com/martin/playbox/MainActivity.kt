package com.martin.playbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.martin.core.AuthStates
import com.martin.core.SessionManager
import com.martin.core.pref.PrefUtils
import com.martin.core.pref.Prefs
import com.martin.playbox.ui.theme.PlayBoxTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var prefUtils: PrefUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val user = prefUtils.getUserDetails()
        if(user?.userId!= null){
            SessionManager.currentAuthState.value = AuthStates.AUTHORISED
        }
        setContent {
            PlayBoxTheme {
                PlayBoxApp()
            }
        }
    }
}