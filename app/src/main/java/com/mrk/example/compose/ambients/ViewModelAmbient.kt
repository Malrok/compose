package com.mrk.example.compose.ambients

import androidx.compose.ambientOf
import com.mrk.example.compose.viewmodels.MainViewModel

val ViewModelAmbient = ambientOf<MainViewModel> { error("view model has not been set") }