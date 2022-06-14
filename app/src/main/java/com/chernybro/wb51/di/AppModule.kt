package com.chernybro.wb51.di


import android.content.Context
import com.chernybro.wb51.data.local.HeroListStorage
import com.chernybro.wb51.data.local.HeroListStorageImpl
import com.chernybro.wb51.data.remote.service.HeroListApi
import com.chernybro.wb51.data.remote.service.HeroListApiImpl
import com.chernybro.wb51.data.repository.HeroesRepository
import com.chernybro.wb51.data.repository.HeroesRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                val url = Constants.HEROES_BASE_URL
//                request.url(url)
//                chain.proceed(request.build())
//            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideHeroListApi(okHttpClient: OkHttpClient, moshi: Moshi): HeroListApi {
        return HeroListApiImpl(okHttpClient, moshi)
    }

    @Provides
    @Singleton
    fun provideHeroListStorage(@ApplicationContext context: Context ): HeroListStorage {
        return HeroListStorageImpl(context)
    }

    @Provides
    @Singleton
    fun provideHeroesRepository(heroListApi: HeroListApi, heroListStorage: HeroListStorage): HeroesRepository {
        return HeroesRepositoryImpl(heroListApi, heroListStorage)
    }
}