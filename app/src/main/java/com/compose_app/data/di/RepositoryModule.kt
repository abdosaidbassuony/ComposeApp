package com.compose_app.data.di

import com.compose_app.data.remote.ApiService
import com.compose_app.data.repository.CategoriesRepositoryImp
import com.compose_app.domain.repository.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideYourRepository(apiService: ApiService): CategoriesRepository {
        return CategoriesRepositoryImp(apiService)
    }
}
