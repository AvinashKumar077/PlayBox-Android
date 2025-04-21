package com.martin.core.di

import com.martin.core.pref.PrefImpl
import com.martin.core.pref.PrefUtils
import com.martin.core.pref.PrefUtils.PrefUtilsImpl
import com.martin.core.pref.Prefs
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {
    @Binds
    abstract fun bindPrefs(
        prefsImpl: PrefImpl
    ): Prefs

    @Binds
    abstract fun bindPrefUtils(
        prefUtilsImpl: PrefUtilsImpl
    ): PrefUtils

}