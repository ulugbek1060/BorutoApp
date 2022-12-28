package com.android.borutoapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.android.borutoapp.domain.model.HeroRemoteKeys

@Dao
interface HeroRemoteKeysDao {

    @Query("SELECT * FROM hero_remote_key_table WHERE id = :heroId")
    suspend fun getRemoteKeys(heroId: Int): HeroRemoteKeys?

    @Insert(onConflict = REPLACE)
    suspend fun addAllRemoteKeys(heroRemoteKeys: List<HeroRemoteKeys>)

    @Query("DELETE FROM hero_remote_key_table")
    suspend fun deleteAllRemoteKeys()
}