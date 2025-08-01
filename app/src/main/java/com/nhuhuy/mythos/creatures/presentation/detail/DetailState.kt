package com.nhuhuy.mythos.creatures.presentation.detail

import androidx.compose.runtime.Stable
import com.nhuhuy.mythos.core.ui.component.ScreenState
import com.nhuhuy.mythos.creatures.domain.model.Creature


@Stable
data class DetailState(
    val creature: Creature? = null,
    val image: String = "",
    val screenState: ScreenState = ScreenState.Loading
)
