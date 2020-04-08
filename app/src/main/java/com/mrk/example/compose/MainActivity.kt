package com.mrk.example.compose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Providers
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.mrk.example.compose.ambients.ViewModelAmbient
import com.mrk.example.compose.navigation.NavigationStatus
import com.mrk.example.compose.navigation.Root
import com.mrk.example.compose.navigation.navigateTo
import com.mrk.example.compose.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel: MainViewModel by viewModels()

        setContent {
            MaterialTheme {
                Providers(
                    ViewModelAmbient provides mainViewModel
                ) {
                    Root.Content()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (NavigationStatus.currentScreen is Root.Routing.Detail) {
            navigateTo(Root.Routing.List)
        } else {
            super.onBackPressed()
        }
    }
}
