package com.mrk.example.compose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Providers
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.mrk.example.compose.ambients.ViewModelAmbient
import com.mrk.example.compose.navigation.Root
import com.mrk.example.compose.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val backPressHandler = BackPressHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel: MainViewModel by viewModels()

        setContent {
            MaterialTheme {
                Providers(
//                    AmbientBackPressHandler provides backPressHandler,
                    ViewModelAmbient provides mainViewModel
                ) {
                    Root.Content()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!backPressHandler.handle()) {
            super.onBackPressed()
        }
    }
}
