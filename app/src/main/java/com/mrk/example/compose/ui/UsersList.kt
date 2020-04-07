package com.mrk.example.compose.ui

import android.util.Log
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.wrapContentSize
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.ListItem
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.material.ripple.ripple
import androidx.ui.res.stringResource
import androidx.ui.unit.Dp
import com.mrk.example.compose.models.User
import com.mrk.example.compose.R
import com.mrk.example.compose.ambients.ViewModelAmbient
import com.mrk.example.compose.effects.observe

interface UsersList {
    companion object {
        @Composable
        fun Content() {
            Scaffold(
                topAppBar = {
                    TopAppBar(title = {
                        Text(stringResource(id = R.string.app_name))
                    })
                },
                bodyContent = {
                    usersList()
                }
            )
        }
    }
}

@Composable
fun usersList() {
    val viewModel = ViewModelAmbient.current
    val query = observe(data = viewModel.getUsers())

    if (query != null && !query.loading) {
        if (!query.error) {
            AdapterList(data = query.data) {
                usersListItem(user = it)
            }
        } else {
            Text(text = "an error occurred")
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize() + Modifier.wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun usersListItem(user: User) {
    Clickable(
        onClick = {
            Log.d("clicked", user.id + " " + user.first_name!!)
        },
        modifier = Modifier.ripple()
    ) {
        ListItem(
            icon = {
                ImageNetwork(url = user.picture, width = Dp(64f), height = Dp(64f))
            },
            text = {
                Text(text = "${user.first_name} ${user.last_name}")
            }
        )
    }

}