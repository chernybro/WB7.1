package com.chernybro.wb51.data.repository

import coil.network.HttpException
import com.chernybro.wb51.R
import com.chernybro.wb51.data.local.HeroListStorage
import com.chernybro.wb51.data.remote.models.getHeroDetailsFromDTO
import com.chernybro.wb51.data.remote.models.getHeroItemFromDTO
import com.chernybro.wb51.data.remote.service.HeroListApi
import com.chernybro.wb51.domain.models.HeroDetailsItem
import com.chernybro.wb51.domain.models.HeroItem
import com.chernybro.wb51.presentation.models.ScreenState
import java.io.IOException

class HeroesRepositoryImpl(
    private val heroListApi: HeroListApi,
    private val heroListStorage: HeroListStorage
) : HeroesRepository {

    override suspend fun getHeroes(): ScreenState<List<HeroItem>> {
        return try {
            ScreenState.Success(data = heroListStorage.getHeroes()?.map{ it.getHeroItemFromDTO() } ?: emptyList() )
        } catch (io: IOException) {
            try {
                val heroes = heroListApi.getHeroes()
                if (heroes != null) {
                    heroListStorage.saveHeroes(heroes)
                }
                ScreenState.Success(data = heroes?.map { it.getHeroItemFromDTO() } ?: emptyList())
            } catch (e: HttpException) {
                ScreenState.Error(R.string.error_io_exception)
            }
        } catch (e: Exception) {
            ScreenState.Error(R.string.error_not_found)
        }
    }

    override suspend fun getHero(id: Int): ScreenState<HeroDetailsItem> {
        return try {
            val storageHero = heroListStorage.getHero(id)
            if (storageHero != null) {
                ScreenState.Success(data = storageHero.getHeroDetailsFromDTO())
            } else {
                try {
                    val apiHero = heroListApi.getHero(id)
                    if (apiHero != null) {
                        ScreenState.Success(data = apiHero.getHeroDetailsFromDTO())
                    } else {
                        ScreenState.Error(R.string.error_not_found)
                    }
                } catch (e: HttpException) {
                    ScreenState.Error(R.string.error_io_exception)
                }
            }
        } catch (io: IOException) {
            try {
                val apiHero = heroListApi.getHero(id)
                if (apiHero != null) {
                    ScreenState.Success(data = apiHero.getHeroDetailsFromDTO())
                } else {
                    ScreenState.Error(R.string.error_not_found)
                }
            } catch (e: HttpException) {
                ScreenState.Error(R.string.error_io_exception)
            }
        } catch (e: Exception) {
            ScreenState.Error(R.string.error_not_found)
        }
    }
}