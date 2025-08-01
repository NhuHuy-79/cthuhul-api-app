package com.nhuhuy.mythos.creatures.presentation.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.nhuhuy.mythos.R

@Composable
fun ImageContainerDialog(
    onDismiss: () -> Unit = {},
    key: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black.copy(alpha = 0.8f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = key,
                        contentDescription = "image",
                        modifier = Modifier.wrapContentSize(),
                        placeholder = painterResource(id = R.drawable.img_placholder)
                    )
                }
            }
        )
    }
}