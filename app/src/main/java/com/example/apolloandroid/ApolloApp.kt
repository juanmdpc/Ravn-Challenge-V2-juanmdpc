package com.example.apolloandroid

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * It's strict necessary for we can use Hilt, then
 * it can create from there the dependency tree for the application.
 * It generates all the Components (AppComponent, ActivityComponent) and Scopes.
 */
@HiltAndroidApp
class ApolloApp : Application()