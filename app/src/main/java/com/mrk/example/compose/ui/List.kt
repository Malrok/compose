package com.mrk.example.compose.ui

import android.util.Log
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.layout.Row
import androidx.ui.layout.padding
import androidx.ui.unit.Dp
import com.moventes.moventest.android.models.User


@Composable
fun usersList(users: List<User>?) {
    if (users != null) {
        AdapterList(data = users) {
            usersListItem(user = it)
        }
    } else {
        Text(text = "no users")
    }
}

@Composable
fun usersListItem(user: User) {
    Clickable(
        onClick = {
            Log.d("clicked", user.first_name!!)
        }
    ) {
        Row(
            modifier = Modifier.padding(start = Dp(8f), end = Dp(8f), top = Dp(4f), bottom = Dp(4f))
        ) {
            ImageNetwork(url = user.picture, width = Dp(64f), height = Dp(64f))
            Text(text = "Hello ${user.first_name}!")
        }
    }

}