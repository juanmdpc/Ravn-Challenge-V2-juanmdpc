package com.example.apolloandroid.repository

import com.apollographql.apollo.api.Response
import com.example.starwars.DetailPersonQuery
import com.example.starwars.PeopleListQuery

/**
 * Repository for fetch data on Star Wars API.
 */
interface PeopleRepository {

    /* Suspend functions, which are important in Coroutines, used for request data, then
        it might take along time therefore we could wait for them without blocking.
        This functions can be paused and resumed at a later time. */

    suspend fun searchPeople(): Response<PeopleListQuery.Data>

    suspend fun searchDetail(id: String): Response<DetailPersonQuery.Data>
}