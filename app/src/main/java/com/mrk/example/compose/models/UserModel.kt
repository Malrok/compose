package com.mrk.example.compose.models

data class UserModel(
    var id: String? = null,
    var firstName: String = "",
    var lastName: String = "",
    var description: String = "",
    var email: String = "",
    var picture: String = ""
) {
    fun toEntity() = UserEntity(
        first_name = this.firstName,
        last_name = this.lastName,
        description = this.description,
        email = this.email,
        picture = this.picture
    )
}