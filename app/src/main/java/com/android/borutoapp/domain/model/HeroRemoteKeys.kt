package com.android.borutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.borutoapp.utils.Constants.HERO_REMOTE_KEY_DATABASE_TABLE

@Entity(tableName = HERO_REMOTE_KEY_DATABASE_TABLE)
data class HeroRemoteKeys(
   @PrimaryKey(autoGenerate = false) val id: Int,
   val prevPage: Int?,
   val nextPage: Int?,
   val lastUpdated: Long?
)