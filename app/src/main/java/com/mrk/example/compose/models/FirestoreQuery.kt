package com.mrk.example.compose.models

data class FirestoreQuery<D>(
    var loading: Boolean = true,
    var error: Boolean = false,
    var data: List<D> = emptyList()
)