package com.chernybro.wb51.presentation.hero_list_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chernybro.wb51.data.repository.HeroesRepository
import com.chernybro.wb51.domain.models.HeroItem
import com.chernybro.wb51.presentation.models.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroesListViewModel @Inject constructor(
    private val heroesRepository: HeroesRepository
) : ViewModel() {
    private val _items: MutableLiveData<List<HeroItem>> = MutableLiveData(listOf())
    val items: LiveData<List<HeroItem>> = _items

    private val _errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: LiveData<Int> = _errorMessage

    init {
        fetchHeroes()
    }

    fun fetchHeroes(){
        viewModelScope.launch(Dispatchers.IO) {
            when (val state = heroesRepository.getHeroes()) {
                is ScreenState.Success -> _items.postValue(state.data)
                is ScreenState.Error -> _errorMessage.postValue(state.error)
            }
        }
    }
}