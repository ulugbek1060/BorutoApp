package com.android.borutoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.domain.model.HeroRemoteKeys

@Database(
    entities = [Hero::class, HeroRemoteKeys::class],
    version = 2
)
@TypeConverters(DatabaseConverter::class)
abstract class BorutoDatabase : RoomDatabase() {
    abstract fun heroDao(): HeroDao
    abstract fun heroRemoteKeysDao(): HeroRemoteKeysDao
}