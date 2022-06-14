package com.chernybro.wb51.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.chernybro.wb51.data.remote.models.HeroStatsDTO
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class HeroListStorageImpl(
    private val context: Context
) : HeroListStorage {

    companion object {
        private const val DOTA_HEROES_FILE_NAME = "dota_heroes"
    }

    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    override fun saveHeroes(heroes: List<HeroStatsDTO>) {
        val adapter: JsonAdapter<List<HeroStatsDTO>> = moshi.adapter(Types.newParameterizedType(List::class.java, HeroStatsDTO::class.java))
        val heroesJson = adapter.toJson(heroes)
        val fileOutputStream = context.openFileOutput(DOTA_HEROES_FILE_NAME, MODE_PRIVATE)
        fileOutputStream.write(heroesJson.toByteArray())
        fileOutputStream.close()
    }

    override fun getHeroes(): List<HeroStatsDTO>? {
        val adapter: JsonAdapter<List<HeroStatsDTO>> = moshi.adapter(Types.newParameterizedType(List::class.java, HeroStatsDTO::class.java))
        val fileInputStream = context.openFileInput(DOTA_HEROES_FILE_NAME)
        val bytes = ByteArray(fileInputStream.available())
        fileInputStream.read(bytes)
        val heroesJson = String(bytes)
        fileInputStream?.close()
        return adapter.fromJson(heroesJson)
    }

    override fun getHero(id: Int): HeroStatsDTO? {
        val adapter: JsonAdapter<List<HeroStatsDTO>> = moshi.adapter(Types.newParameterizedType(List::class.java, HeroStatsDTO::class.java))
        val fileInputStream = context.openFileInput(DOTA_HEROES_FILE_NAME)
        val bytes = ByteArray(fileInputStream.available())
        fileInputStream.read(bytes)
        val heroesJson = String(bytes)
        fileInputStream?.close()
        val heroes = adapter.fromJson(heroesJson)
        return heroes?.first { hero -> hero.id == id }
    }
}