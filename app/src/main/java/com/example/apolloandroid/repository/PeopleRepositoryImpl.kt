package com.example.apolloandroid.repository

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.apolloandroid.networking.StarWarsApi
import com.example.starwars.DetailPersonQuery
import com.example.starwars.PeopleListQuery
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val webService: StarWarsApi
    ) : PeopleRepository {

    /**
     * Function for request a people list (star wars characters) in our API.
     */
    override suspend fun searchPeople(): Response<PeopleListQuery.Data> {
        return webService.getApolloClient().query(PeopleListQuery()).await()
    }

    /**
     * Function for request the detail of a person (character specified) in our API.
     */
    override suspend fun searchDetail(id: String): Response<DetailPersonQuery.Data> {
        return  webService.getApolloClient().query(DetailPersonQuery(id)).await()
    }
}