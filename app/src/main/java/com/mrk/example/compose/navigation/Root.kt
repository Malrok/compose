package com.mrk.example.compose.navigation

import androidx.compose.Composable
import androidx.compose.Model
import com.mrk.example.compose.ui.UserDetail
import com.mrk.example.compose.ui.UsersList

interface Root {
    sealed class Routing {
        object List : Routing()
        data class Detail(val id: String) : Routing()
    }

    companion object {
        @Composable
        fun Content() {
            when (NavigationStatus.currentScreen) {
                is Routing.List -> UsersList.Content()
                is Routing.Detail -> UserDetail.Content(
                    id = (NavigationStatus.currentScreen as Routing.Detail).id
                )
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