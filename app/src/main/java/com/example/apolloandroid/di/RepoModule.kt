package com.example.apolloandroid.di

import com.example.apolloandroid.networking.StarWarsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * RepoModule will create an ApolloClient instance whenever necessary.
 * Also it will be alive outside of some Activity or Fragment.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    /**
     * Function for provide Apollo Client instance.
     * We use Singleton for the dependency exists as long as the application is alive.
     * We use Provides for manage the constructor injection problem about third-party library.
     */
    @Singleton
    @Provides
    fun provideWebService() = StarWarsApi()
}