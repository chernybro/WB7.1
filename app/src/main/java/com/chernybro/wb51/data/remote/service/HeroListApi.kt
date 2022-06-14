package com.chernybro.wb51.data.remote.service

import com.chernybro.wb51.data.remote.models.HeroStatsDTO

interface HeroListApi {

    suspend fun getHeroes(): List<HeroStatsDTO>?

    suspend fun getHero(id: Int) : HeroStatsDTO?

}