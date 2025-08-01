package com.nhuhuy.mythos.core.ui.component

sealed class ScreenState {
    data object Loading : ScreenState()
    data class Error(val exception: Exception) : ScreenState()
    data object Success : ScreenState()
}