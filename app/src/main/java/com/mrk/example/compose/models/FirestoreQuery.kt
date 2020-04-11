package com.mrk.example.compose.models

data class FirestoreQuery<D>(
    var loading: Boolean = true,
    var error: Boolean = false,
    var data: D
)