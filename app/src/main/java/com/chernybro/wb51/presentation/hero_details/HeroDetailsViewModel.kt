package com.chernybro.wb51.presentation.hero_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chernybro.wb51.data.repository.HeroesRepository
import com.chernybro.wb51.domain.models.HeroDetailsItem
import com.chernybro.wb51.presentation.models.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDetailsViewModel @Inject constructor(
    private val heroesRepository: HeroesRepository
) : ViewModel() {
    private val _heroDetails: MutableLiveData<HeroDetailsItem> = MutableLiveData()
    val heroDetails: LiveData<HeroDetailsItem> = _heroDetails

    private val _errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: LiveData<Int> = _errorMessage

    fun getHeroDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val state = heroesRepository.getHero(id)) {
                is ScreenState.Success -> _heroDetails.postValue(state.data)
                is ScreenState.Error -> _errorMessage.postValue(state.error)
            }
        }
    }
}