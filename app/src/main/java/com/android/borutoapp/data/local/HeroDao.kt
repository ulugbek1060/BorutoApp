package com.android.borutoapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.android.borutoapp.domain.model.Hero

@Dao
interface HeroDao {

    @Insert(onConflict = REPLACE)
    suspend fun addHeroes(heroes: List<Hero>)

    @Query("SELECT * FROM HERO_TABLE WHERE ID=:heroId")
    suspend fun getSelectedHero(heroId: Int): Hero

    @Query("SELECT * FROM HERO_TABLE ORDER BY id ASC")
    fun getAllHeroes(): PagingSource<Int, Hero>

    @Query("DELETE FROM HERO_TABLE")
    suspend fun deleteAllHeroes()

}