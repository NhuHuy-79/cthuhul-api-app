package com.nhuhuy.mythos.core.di

import android.content.Context
import androidx.room.Room
import com.nhuhuy.mythos.creatures.data.local.room.CreatureDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CreatureDatabase {
        return Room.databaseBuilder(
            context,
            CreatureDatabase::class.java,
            "creatures.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: CreatureDatabase) = database.creatureDao
}