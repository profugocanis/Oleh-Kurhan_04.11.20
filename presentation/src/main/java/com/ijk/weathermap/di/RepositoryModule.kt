package com.ijk.weathermap.di

import com.ijk.data.repository.WeatherRepositoryImpl
import com.ijk.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {
    @Binds
    fun getUserRepository(p: WeatherRepositoryImpl): WeatherRepository
}
