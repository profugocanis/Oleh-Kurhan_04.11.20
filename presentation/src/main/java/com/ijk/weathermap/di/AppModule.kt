package com.ijk.weathermap.di

import android.content.Context
import com.ijk.weathermap.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.security.AccessControlContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun dispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Named("weatherApiKey")
    fun weatherApiKey(@ApplicationContext context: Context): String {
        return context.getString(R.string.weather_api)
    }
}