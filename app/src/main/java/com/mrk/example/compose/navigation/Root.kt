package com.mrk.example.compose.navigation

import androidx.compose.Composable
import com.mrk.example.compose.ui.UsersList

interface Root {
    sealed class Routing {
        object List : Routing()
        data class Detail(val id: String) : Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing = Routing.List) {
//            Router(defaultRouting) { backStack ->
//                when (backStack.last()) {
//                    is Routing.List -> UsersList.Content()
//                    is Routing.Detail -> UserDetail.Content((backStack.last() as Routing.Detail).id)
//                }
//            }
            UsersList.Content()
        }
    }
}