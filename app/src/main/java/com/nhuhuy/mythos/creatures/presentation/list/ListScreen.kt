package com.nhuhuy.mythos.creatures.presentation.list

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nhuhuy.mythos.R
import com.nhuhuy.mythos.core.ui.component.ErrorSection
import com.nhuhuy.mythos.core.ui.component.LoadingSection
import com.nhuhuy.mythos.core.ui.component.ScreenState
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.presentation.list.component.CreatureItem
import com.nhuhuy.mythos.creatures.presentation.list.component.MythosBottomSheet
import com.nhuhuy.mythos.creatures.presentation.list.component.MythosSearchBar
import com.nhuhuy.mythos.creatures.presentation.list.component.TabScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    modifier: Modifier,
    onDetailClick: (Int) -> Unit,
    onGoWiki: () -> Unit,
    viewModel: ListViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val creatures by viewModel.uiList.collectAsStateWithLifecycle()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isShowBottomSheet by remember { mutableStateOf(false) }


    Log.d("List Screen", "$state")
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                actions = {
                    MythosSearchBar(
                        onSearchCancel = {
                            viewModel.updateSearchQuery("")
                            viewModel.changeSearchStatus(false)
                            focusManager.clearFocus()
                        },
                        visible = state.isSearching,
                        modifier = Modifier,
                        query = query,
                        onSearch = viewModel::updateSearchQuery,
                        focusRequester = focusRequester
                    )

                    IconButton(
                        onClick = { viewModel.changeSearchStatus(true) }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "search",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    IconButton(
                        onClick = { isShowBottomSheet = true }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "more",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                },
            )
        },
    )
    { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {

            if (isShowBottomSheet) {
                MythosBottomSheet(
                    onDismiss = { isShowBottomSheet = false },
                    onGoWiki = {
                        isShowBottomSheet = false
                        onGoWiki()
                    },
                    onAboutUs = {

                    }
                )
            }

            when (state.screenState) {
                is ScreenState.Error -> ErrorSection(
                    {}
                )

                ScreenState.Loading -> LoadingSection()
                ScreenState.Success -> TabScreen(
                    all = creatures,
                    onDetailClick = onDetailClick
                )
            }
        }
    }

}


@Composable
fun PagerSection(
    modifier: Modifier,
    creatures: List<Creature>,
    onDetailClick: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        if (creatures.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_book),
                    contentDescription = "empty",
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "No creature found!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(creatures, key = { it.id }) { item ->
                CreatureItem(
                    creature = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                        .clickable {
                            onDetailClick(item.id)
                        }
                        .animateItem()
                )
            }

        }
    }
}

