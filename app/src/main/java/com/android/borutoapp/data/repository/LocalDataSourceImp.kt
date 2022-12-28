package com.android.borutoapp.data.repository

import com.android.borutoapp.data.local.BorutoDatabase
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.domain.repository.LocalDataSource
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
   borutoDatabase: BorutoDatabase
) : LocalDataSource {

   private val heroDao = borutoDatabase.heroDao()

   override suspend fun getSelectedHero(heroId: Int): Hero =
      heroDao.getSelectedHero(heroId = heroId)

}