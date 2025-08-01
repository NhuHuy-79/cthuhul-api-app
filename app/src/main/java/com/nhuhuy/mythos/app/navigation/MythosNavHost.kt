package com.nhuhuy.mythos.app.navigation


import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nhuhuy.mythos.creatures.presentation.detail.DetailScreen
import com.nhuhuy.mythos.creatures.presentation.detail.DetailViewModel
import com.nhuhuy.mythos.creatures.presentation.list.ListScreen
import com.nhuhuy.mythos.creatures.presentation.list.ListViewModel


@Composable

fun MythosNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.List
    ) {
        composable<Route.List>(
            exitTransition = { fadeOut(tween(500, easing = LinearEasing)) },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(400, easing = LinearEasing)
                )
            }
        ) {
            val listVM: ListViewModel = hiltViewModel()
            ListScreen(
                modifier = Modifier,
                onDetailClick = { id ->
                    navHostController.navigate(Route.Detail(id))
                },
                viewModel = listVM
            )
        }

        composable<Route.Detail>(
            enterTransition = { fadeIn(tween(500, easing = FastOutSlowInEasing)) },
            popExitTransition = {
              slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(400, easing = LinearEasing)
                )
            }
        ) { entry ->
            val detailVM: DetailViewModel = hiltViewModel()
            DetailScreen(
                id = entry.arguments?.getInt("id") ?: 0,
                modifier = Modifier,
                viewModel = detailVM,
                onMoreClick = { },
                onCategorySearch = {},
                onNavigateBack = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}