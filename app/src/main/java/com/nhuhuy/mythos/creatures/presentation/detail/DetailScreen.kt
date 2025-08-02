package com.nhuhuy.mythos.creatures.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nhuhuy.mythos.core.ui.component.ErrorSection
import com.nhuhuy.mythos.core.ui.component.LoadingSection
import com.nhuhuy.mythos.core.ui.component.ScreenState
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.capitalizeName
import com.nhuhuy.mythos.creatures.presentation.detail.component.DetailContent
import com.nhuhuy.mythos.creatures.presentation.detail.component.DetailImageSlider
import com.nhuhuy.mythos.creatures.presentation.detail.component.DetailOtherName
import com.nhuhuy.mythos.creatures.presentation.detail.component.DetailTag
import com.nhuhuy.mythos.creatures.presentation.detail.component.ImageContainerDialog
import com.nhuhuy.mythos.creatures.presentation.detail.component.MythosDialog

@Composable
fun DetailScreen(
    id: Int,
    modifier: Modifier,
    viewModel: DetailViewModel,
    onMoreClick: (String, String) -> Unit,
    onCategorySearch: () -> Unit,
    onNavigateBack: () -> Unit
) {
    viewModel.provideDetail(id)
    val state by viewModel.state

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state.screenState) {
            is ScreenState.Error -> ErrorSection {
                viewModel.provideDetail(id)
            }

            ScreenState.Loading -> LoadingSection()
            ScreenState.Success -> SuccessDetailSection(
                onNavigateBack = onNavigateBack,
                onMoreClick = onMoreClick,
                creature = state.creature,
                onCategorySearch,
                state = state,
                onImageChoose = viewModel::updateImageState
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessDetailSection(
    onNavigateBack: () -> Unit,
    onMoreClick: (String, String) -> Unit,
    creature: Creature?,
    onCategorySearch: () -> Unit,
    onImageChoose: (String) -> Unit,
    state: DetailState
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    var isShowImage by remember { mutableStateOf(false) }
    val defaultText = "Undefined"
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = creature?.name?.capitalizeName() ?: defaultText,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { isDialogOpen = true }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "more information",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValue ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                creature?.let {
                    DetailImageSlider(
                        modifier = Modifier.fillMaxWidth(),
                        images = creature.img,
                        onImageClick = {
                            onImageChoose(it)
                            isShowImage = true
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DetailTag(
                        appearance = creature.category,
                        author = creature.author
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DetailOtherName(
                        others = creature.nicks.map { it.capitalizeName() }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DetailContent(
                        name = creature.nicks.firstOrNull()?.capitalizeName()
                            ?: creature.name.capitalizeName(),
                        overview = creature.overview
                    )
                }

            }

            if (isShowImage) {
                ImageContainerDialog(
                    onDismiss = { isShowImage = false },
                    key = state.image
                )
            }

            if (isDialogOpen) {
                MythosDialog(
                    onDismiss = { isDialogOpen = false },
                    onConfirm = {
                        onMoreClick(creature?.wikiUrl ?: "", creature?.name ?: "")
                        isDialogOpen = false
                    }
                )
            }
        }
    }
}
