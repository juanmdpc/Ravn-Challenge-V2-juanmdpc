package com.example.apolloandroid.di

import com.example.apolloandroid.repository.PeopleRepository
import com.example.apolloandroid.repository.PeopleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

/**
 * ViewModelModule will allow us do queries/request functions to API inside ViewModels.
 * Also it needs to outlive any Activity.
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    /**
     * Function will return our PeopleRepository for use its queries/request functions.
     * We use bind for manage the constructor injection problem about interfaces.
     */
    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repo: PeopleRepositoryImpl): PeopleRepository
}