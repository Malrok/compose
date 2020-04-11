package com.mrk.example.compose.navigation

import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.animation.Crossfade
import com.mrk.example.compose.ui.pages.UserDetail
import com.mrk.example.compose.ui.pages.UsersList

interface Root {
    sealed class Routing {
        object List : Routing()
        data class Detail(val id: String) : Routing()
    }

    companion object {
        @Composable
        fun Content() {
            Crossfade(current = NavigationStatus.currentScreen) { screen ->
                when (screen) {
                    is Routing.List -> UsersList.Content()
                    is Routing.Detail -> UserDetail.Content(id = screen.id)
                }
            }
        }
    }
}

@Model
object NavigationStatus {
    var currentScreen: Root.Routing = Root.Routing.List
}

fun navigateTo(currentScreen: Root.Routing) {
    NavigationStatus.currentScreen = currentScreen
}