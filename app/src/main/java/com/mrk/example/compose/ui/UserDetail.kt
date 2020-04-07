package com.mrk.example.compose.ui

import androidx.compose.Composable
import androidx.ui.foundation.Text

interface UserDetail {
    companion object {
        @Composable
        fun Content(id: String) {
            Text(text = id)
        }
    }
}