package com.martin.core.utils.extensions

import com.martin.core.R
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


fun Activity.hideKeyboard() {
    try{
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }catch (e: Exception){
        Log.e("hideKeyboard", "hideKeyboard: $e")
    }
}

fun Context?.hideKeyboard() {
    this?.getActivity()?.hideKeyboard()
}

fun <T : Parcelable?> Activity.getParcelableExtra(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        try {
            intent?.getParcelableExtra(name, clazz)
        } catch (_: Exception) {
            intent?.getParcelableExtra<T>(name)
        }
    } else {
        intent?.getParcelableExtra<T>(name)
    }
}

fun <T : Parcelable?> Intent.getParcelable(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        try {
            getParcelableExtra(name, clazz)
        } catch (_: Exception) {
            getParcelableExtra<T>(name)
        }
    } else {
        getParcelableExtra<T>(name)
    }
}

fun <T : Parcelable?> Bundle.getParcelableExtra(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        try {
            getParcelable(name, clazz)
        } catch (_: Exception) {
            getParcelable<T>(name)
        }
    } else {
        getParcelable<T>(name)
    }
}


fun Context.getActivity(): ComponentActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is ComponentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

fun Context.requireActivity(): ComponentActivity {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is ComponentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    throw IllegalStateException("Activity not found")
}

fun ComponentActivity.edgeToEdge() {
    enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT),
        navigationBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
    )
}

fun FragmentManager.replaceFragmentWithoutAnimation(@IdRes id: Int, fragment: Fragment, tag: String? = null) {
    this.beginTransaction()
        .replace(id,fragment,tag)
        .commitAllowingStateLoss()
}



