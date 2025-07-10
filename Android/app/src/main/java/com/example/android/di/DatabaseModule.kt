package com.example.android.di

import android.content.Context
import androidx.room.Room
import com.example.android.data.AppDatabase
import com.example.android.data.local.LocationNoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase = Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()

    @Provides
    fun provideLocationNoteDao(database: AppDatabase): LocationNoteDao = database.locationNoteDao()
}