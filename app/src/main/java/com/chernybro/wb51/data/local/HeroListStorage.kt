package com.chernybro.wb51.data.local

import com.chernybro.wb51.data.remote.models.HeroStatsDTO

interface HeroListStorage {

    fun saveHeroes(heroes: List<HeroStatsDTO>)

    fun getHeroes(): List<HeroStatsDTO>?

    fun getHero(id: Int): HeroStatsDTO?
}