package com.example.apolloandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.apolloandroid.repository.PeopleRepository
import com.example.apolloandroid.view.state.ViewState
import com.example.starwars.DetailPersonQuery
import com.example.starwars.PeopleListQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val repository: PeopleRepository
) : ViewModel() {

    private val _peopleList by lazy {
        MutableLiveData<ViewState<Response<PeopleListQuery.Data>>>()
    }

    val peopleList:
            LiveData<ViewState<Response<PeopleListQuery.Data>>>
        get() = _peopleList

    /**
     * This method sets the PeopleFragment ViewState:
     * Loading, is first ever for each call;
     * Success if the call was successful, otherwise Error.
     */
    fun queryPeopleList() = viewModelScope.launch {
        _peopleList.postValue(ViewState.Loading())
        try {
            val response = repository.searchPeople()
            _peopleList.postValue(ViewState.Success(response))
            Log.d("ApolloResponse", response.data.toString())
        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _peopleList.postValue(ViewState.Error("Error fetching people"))
        }
    }

    private val _detail by lazy {
        MutableLiveData<ViewState<Response<DetailPersonQuery.Data>>>()
    }
    val detail: LiveData<ViewState<Response<DetailPersonQuery.Data>>>
        get() = _detail

    /**
     * This method sets the DetailFragment ViewState:
     * Loading, is first ever for each call;
     * Success if the call was successful, otherwise Error.
     */
    fun queryPersonDetail(id: String) = viewModelScope.launch {
        _detail.postValue(ViewState.Loading())
            try {
                val response = repository.searchDetail(id)
                _detail.postValue(ViewState.Success(response))
            } catch (e: ApolloException) {
                Log.e("ApolloException", "Failure", e)
                _detail.postValue(ViewState.Error("Error fetching details"))
            }
    }
}
