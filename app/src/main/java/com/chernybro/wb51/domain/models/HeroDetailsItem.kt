package com.chernybro.wb51.domain.models

data class HeroDetailsItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val mana: HeroMana,
    val health: HeroHealth,
    val stats: HeroStats,
)

data class HeroStats(
    val attributes: HeroAttributes,
    val mobility: HeroMobility,
    val attack: HeroAttack,
    val defence: HeroDefence
)

data class HeroAttributes(
    val agility: HeroAgility,
    val intelligence: HeroIntelligence,
    val strength: HeroStrength
)

data class HeroDefence(
    val magicResistant: Int,
    val armor: Double
)

data class HeroMobility(
    val speed: Int
)

data class HeroAttack(
    val max: Int,
    val min: Int,
    val rate: Double,
    val range: Int,
    val type: String
)

data class HeroHealth(
    val baseValue: Int,
    val regen: Double,
)

data class HeroMana(
    val baseValue: Int,
    val regen: Double,
)

data class HeroAgility(
    val gain: Double,
    val base: Int,
)

data class HeroIntelligence(
    val gain: Double,
    val base: Int,
)

data class HeroStrength(
    val gain: Double,
    val base: Int,
)