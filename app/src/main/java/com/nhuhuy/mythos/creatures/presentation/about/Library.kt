package com.nhuhuy.mythos.creatures.presentation.about

data class LibraryUiModel(
    val name: String,
    val version: String,
    val url: String
)

val libraries = listOf(
    LibraryUiModel(
        name = "Jetpack Compose",
        version = "",
        url = ""
    ),

    LibraryUiModel(
        name = "Retrofit",
        version = "2.9.0",
        url = "",
    ),

    LibraryUiModel(
        name = "Hilt",
        version = "2.52",
        url = "",
    ),

    LibraryUiModel(
        name = "Coil - Compose",
        version = "2.5.0",
        url = "",
    ),

    LibraryUiModel(
        name = "Compose Navigation",
        version = "2.8.0",
        url = "",
    ),

    LibraryUiModel(
        name = "Kotlin Serialization",
        version = "1.8.0",
        url = ""
    )
)
