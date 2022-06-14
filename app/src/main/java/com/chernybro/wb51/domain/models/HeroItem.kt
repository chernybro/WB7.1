package com.chernybro.wb51.domain.models

import java.io.Serializable

data class HeroItem(
    val id: Int,
    val name: String,
    val avatar: String? = null,
    val primaryAttr: HeroAttribute
) : Serializable

enum class HeroAttribute {
    Intelligence, Agility, Strength, Unknown
}

