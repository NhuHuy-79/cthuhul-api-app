package com.nhuhuy.mythos.creatures.presentation.detail

import androidx.compose.runtime.Immutable
import com.nhuhuy.mythos.core.ui.component.ScreenState
import com.nhuhuy.mythos.creatures.domain.model.Creature

@Immutable
data class DetailState(
    val creature: Creature? = null,
    val image: String = "",
    val screenState: ScreenState = ScreenState.Loading
)
