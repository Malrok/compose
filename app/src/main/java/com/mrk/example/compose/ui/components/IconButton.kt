package com.mrk.example.compose.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Icon
import androidx.ui.material.ripple.ripple
import androidx.ui.res.vectorResource

@Composable
fun IconButton(@DrawableRes id: Int, onClick: () -> Unit) {
    Clickable(
        onClick = onClick,
        modifier = Modifier.ripple()
    ) {
        Icon(vectorResource(id = id))
    }
}
