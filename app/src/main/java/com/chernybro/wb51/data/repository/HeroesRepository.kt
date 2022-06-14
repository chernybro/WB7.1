package com.chernybro.wb51.data.repository

import com.chernybro.wb51.domain.models.HeroDetailsItem
import com.chernybro.wb51.domain.models.HeroItem
import com.chernybro.wb51.presentation.models.ScreenState

interface HeroesRepository {

    suspend fun getHeroes(): ScreenState<List<HeroItem>>

    suspend fun getHero(id: Int): ScreenState<HeroDetailsItem>
}