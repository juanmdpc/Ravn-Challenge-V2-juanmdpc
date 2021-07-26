package com.example.apolloandroid.networking

import android.os.Looper
import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

private const val BASE_URL = "https://swapi-graphql.netlify.app/.netlify/functions/index"

/**
 * Class for create the ApolloClient instance and
 * does the network call to the Star Wars API endpoint.
 */
class StarWarsApi {

    /**
     * Function for create an ApolloClient instance.
     */
    fun getApolloClient(): ApolloClient {
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the thread main can get an ApolloClient instance"
        }

        val okHttpClient = OkHttpClient.Builder().build()
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}