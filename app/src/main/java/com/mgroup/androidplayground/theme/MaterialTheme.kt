package com.mgroup.androidplayground.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AndroidPlaygroundTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = AndroidPlaygroundColors,
        shapes = AndroidPlaygroundShapes,
        content = content
    )
}