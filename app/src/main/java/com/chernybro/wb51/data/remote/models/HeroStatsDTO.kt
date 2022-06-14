package com.chernybro.wb51.data.remote.models

import com.chernybro.wb51.domain.models.*
import com.chernybro.wb51.utils.Constants
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeroStatsDTO(
    @Json(name = "agi_gain")
    val agilityGain: Double,
    @Json(name = "attack_range")
    val attackRange: Int,
    @Json(name = "attack_rate")
    val attackRate: Double,
    @Json(name = "attack_type")
    val attackType: String,
    @Json(name = "base_agi")
    val baseAgility: Int,
    @Json(name = "base_armor")
    val baseArmor: Double,
    @Json(name = "base_attack_max")
    val baseAttackMax: Int,
    @Json(name = "base_attack_min")
    val baseAttackMin: Int,
    @Json(name = "base_health")
    val baseHealth: Int,
    @Json(name = "base_health_regen")
    val baseHealthRegen: Double,
    @Json(name = "base_int")
    val baseIntelligence: Int,
    @Json(name = "base_mana")
    val baseMana: Int,
    @Json(name = "base_mana_regen")
    val baseManaRegen: Double,
    @Json(name = "base_mr")
    val baseMagicResistant: Int,
    @Json(name = "base_str")
    val baseStrength: Int,
    @Json(name = "hero_id")
    val heroId: Int,
    @Json(name = "icon")
    val icon: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "img")
    val img: String,
    @Json(name = "int_gain")
    val intelligenceGain: Double,
    @Json(name = "localized_name")
    val localizedName: String,
    @Json(name = "move_speed")
    val moveSpeed: Int,
    @Json(name = "primary_attr")
    val primaryAttr: String,
    @Json(name = "str_gain")
    val strengthGain: Double
)

fun HeroStatsDTO.getHeroItemFromDTO(): HeroItem {
    val attribute = when (this.primaryAttr) {
        "agi" -> HeroAttribute.Agility
        "str" -> HeroAttribute.Strength
        "int" -> HeroAttribute.Intelligence
        else -> HeroAttribute.Unknown
    }
    return HeroItem(
        id = id,
        name = localizedName,
        primaryAttr = attribute,
        avatar = Constants.HEROES_IMAGES_BASE + this.icon.substring(1)
    )
}

fun HeroStatsDTO.getHeroDetailsFromDTO(): HeroDetailsItem {
    return HeroDetailsItem(
        id = id,
        name = localizedName,
        imageUrl = Constants.HEROES_IMAGES_BASE + this.img.substring(1),
        mana = HeroMana(
            baseValue = baseMana,
            regen = baseManaRegen
        ),
        health = HeroHealth(
            baseValue = baseHealth,
            regen = baseHealthRegen
        ),
        stats = HeroStats(
            attributes = HeroAttributes(
                agility = HeroAgility(
                    gain = agilityGain,
                    base = baseAgility
                ),
                intelligence = HeroIntelligence(
                    gain = intelligenceGain,
                    base = baseIntelligence
                ),
                strength = HeroStrength(
                    gain = strengthGain,
                    base = baseStrength
                )
            ),
            mobility = HeroMobility(
                speed = moveSpeed
            ),
            attack = HeroAttack(
                min = baseAttackMin,
                max = baseAttackMax,
                rate = attackRate,
                range = attackRange,
                type = attackType
            ),
            defence = HeroDefence(
                magicResistant = baseMagicResistant,
                armor = baseArmor
            )
        )
    )
}