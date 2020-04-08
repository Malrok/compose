package com.mrk.example.compose.ui

import android.util.Log
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.wrapContentSize
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.material.ripple.ripple
import androidx.ui.res.stringResource
import androidx.ui.unit.Dp
import com.mrk.example.compose.models.User
import com.mrk.example.compose.R
import com.mrk.example.compose.ambients.ViewModelAmbient
import com.mrk.example.compose.effects.observe
import com.mrk.example.compose.navigation.Root
import com.mrk.example.compose.navigation.navigateTo

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
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navigateTo(Root.Routing.Detail("-1")) },
                        shape = RoundedCornerShape(50)
                    ) {
                       IconButton(onClick = {}) {
                           Icon(Icons.Filled.Add)
                       } 
                    }
                },
                floatingActionButtonPosition = Scaffold.FabPosition.End
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
            navigateTo(Root.Routing.Detail(user.id!!))
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