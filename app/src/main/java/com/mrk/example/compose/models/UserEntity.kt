package com.mrk.example.compose.models

import androidx.compose.Model

@Model
data class UserEntity(
    var first_name: String = "",
    var last_name: String = "",
    var description: String = "",
    var email: String = "",
    var picture: String = ""
) {
    fun toModel(id: String) = UserModel(
        id = id,
        firstName = this.first_name,
        lastName = this.last_name,
        description = this.description,
        email = this.email,
        picture = this.picture
    )
}