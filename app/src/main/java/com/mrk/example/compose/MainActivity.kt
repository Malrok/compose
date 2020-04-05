package com.mrk.example.compose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Text
import androidx.ui.material.MaterialTheme
import com.moventes.moventest.android.models.User
import com.mrk.example.compose.effects.observe
import com.mrk.example.compose.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel: MainViewModel by viewModels()

        setContent {
            MaterialTheme {
                main(mainViewModel)
            }
        }
    }
}

@Composable
fun main(mainViewModel: MainViewModel) {
    val users = observe(data = mainViewModel.getUsers())

    Greeting(users = users)
}

@Composable
fun Greeting(users: List<User>?) {
    if (users != null) {
        AdapterList(data = users) {
            Text(text = "Hello ${it.first_name}!")
        }
    } else {
        Text(text = "no users")
    }
}
