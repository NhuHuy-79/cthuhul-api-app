package com.nhuhuy.mythos.creatures.presentation.list

import androidx.compose.runtime.Stable
import com.nhuhuy.mythos.core.ui.component.ScreenState
import com.nhuhuy.mythos.creatures.domain.model.Creature

@Stable
data class ListState(
    val data: List<Creature> = emptyList(),
    val isSearching: Boolean = false,
    val isImageZoo: Boolean = false,
    val screenState: ScreenState = ScreenState.Loading
)

