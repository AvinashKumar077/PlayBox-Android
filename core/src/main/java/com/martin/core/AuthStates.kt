package com.martin.core

import androidx.lifecycle.MutableLiveData

object AuthStates {
    const val AUTHORISED = "AUTHORISED"
    const val UNAUTHORISED = "UNAUTHORISED"
    const val DEFAULT = "DEFAULT"
}

object SessionManager {
    val currentAuthState: MutableLiveData<String> = MutableLiveData(AuthStates.DEFAULT)
}