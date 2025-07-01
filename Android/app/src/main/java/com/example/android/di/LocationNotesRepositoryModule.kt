package com.example.android.di

import com.example.android.data.repositroy.LocationNotesRepositoryImpl
import com.example.android.domain.repository.LocationNotesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationNotesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationNotesRepository(
        impl: LocationNotesRepositoryImpl
    ): LocationNotesRepository
}