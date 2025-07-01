package com.example.android.di

import com.example.android.data.repositroy.LocationNoteRepositoryImpl
import com.example.android.domain.repository.LocationNoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationNoteRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationNoteRepository(
        impl: LocationNoteRepositoryImpl
    ): LocationNoteRepository
}