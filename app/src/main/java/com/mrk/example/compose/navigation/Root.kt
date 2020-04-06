package com.mrk.example.compose.navigation

import androidx.compose.Composable
import com.github.zsoltk.compose.router.Router

interface Root {
    sealed class Routing {
        object List: Routing()
        data class Detail(val id: String): Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing) {
            Router(defaultRouting) { backStack ->
                when (val currentRouting = backStack.last()) {
                    is Routing.List ->
                    is Routing.Detail ->
                }
            }
        }
    }
}