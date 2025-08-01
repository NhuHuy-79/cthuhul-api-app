package com.nhuhuy.mythos.creatures.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhuhuy.mythos.core.ui.component.ScreenState
import com.nhuhuy.mythos.creatures.domain.usecase.GetCreatureById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCreatureById: GetCreatureById
) : ViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state get() = _state

    fun updateImageState(key: String) {
        _state.value = _state.value.copy(
            image = key
        )
    }

    fun provideDetail(id: Int) {
        viewModelScope.launch {
            val creature = getCreatureById(id)
            _state.value = _state.value.copy(creature = creature, screenState = ScreenState.Success)
        }
    }
}