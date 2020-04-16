package com.mrk.example.compose.components

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Border
import androidx.ui.foundation.TextField
import androidx.ui.foundation.TextFieldValue
import androidx.ui.graphics.Color
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.material.Surface
import androidx.ui.unit.Dp
import androidx.ui.unit.dp

@Composable
fun EditTextField(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, modifier: Modifier = Modifier.None) {
    Surface(
        modifier = modifier + Modifier.padding(16.dp),
        border = Border(size = Dp.Hairline, color = Color.LightGray)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxSize()
        )
    }
}