package com.arulvakku.lyrics.app.ui.main.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arulvakku.lyrics.app.data.model.Song
import com.arulvakku.lyrics.app.data.model.SongCategory
import com.arulvakku.lyrics.app.data.repository.MainRepository
import com.arulvakku.lyrics.app.utilities.NetworkHelper
import com.arulvakku.lyrics.app.utilities.Resource
import kotlinx.coroutines.launch


class CacheViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    val songCategoriesResult: MutableLiveData<Resource<SongCategory>> = MutableLiveData()
    val songsResult: MutableLiveData<Resource<Song>> = MutableLiveData()

    init {
        getSongCategories()
        getSongs()
    }

    private fun getSongCategories() {
        viewModelScope.launch {
            songCategoriesResult.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getSongCategories().let {
                    if (it.isSuccessful) {
                        songCategoriesResult.postValue(Resource.success(it.body()))
                    } else songCategoriesResult.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else songCategoriesResult.postValue(Resource.error("No internet connection", null))
        }
    }

    private fun getSongs() {
        viewModelScope.launch {
            songsResult.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getSongs().let {
                    if (it.isSuccessful) {
                        songsResult.postValue(Resource.success(it.body()))
                    } else songsResult.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else songsResult.postValue(Resource.error("No internet connection", null))
        }
    }

}


