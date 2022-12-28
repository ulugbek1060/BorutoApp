package com.android.borutoapp.di

import android.content.Context
import androidx.room.Room
import com.android.borutoapp.data.local.BorutoDatabase
import com.android.borutoapp.data.local.HeroDao
import com.android.borutoapp.data.local.HeroRemoteKeysDao
import com.android.borutoapp.utils.Constants.BORUTO_DATABASE
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
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BorutoDatabase {
        return Room.databaseBuilder(context, BorutoDatabase::class.java, BORUTO_DATABASE).build()
    }

    @Provides
    @Singleton
    fun provideHeroDao(db: BorutoDatabase): HeroDao {
        return db.heroDao()
    }


    @Provides
    @Singleton
    fun provideHeroRemoteKeyDao(db: BorutoDatabase): HeroRemoteKeysDao {
        return db.heroRemoteKeysDao()
    }

}