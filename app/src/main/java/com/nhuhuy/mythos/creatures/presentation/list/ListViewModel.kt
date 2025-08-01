package com.nhuhuy.mythos.creatures.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhuhuy.mythos.core.ui.component.ScreenState
import com.nhuhuy.mythos.core.utils.Result
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.filterName
import com.nhuhuy.mythos.creatures.domain.usecase.ObserveDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val observeDataSource: ObserveDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        observeCreatureList()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun changeSearchStatus(value: Boolean) {
        _state.update {
            it.copy(isSearching = value)
        }
    }


    @OptIn(FlowPreview::class)
    val uiList: StateFlow<List<Creature>> = combine(state, searchQuery) { state, query ->
        state.data.filterName(query)
    }
        .debounce(300)
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private fun observeCreatureList() {
        viewModelScope.launch {
            observeDataSource().collect { result ->
                when (result) {
                    is Result.Success -> _state.update {
                        it.copy(
                            data = result.data ?: emptyList(),
                            screenState = ScreenState.Success
                        )
                    }

                    is Result.Failure -> _state.update {
                        it.copy(screenState = ScreenState.Error(exception = result.exception))
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _searchQuery.value = ""
        Log.d("ViewModel Status", "isClear")
    }

}