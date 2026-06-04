package com.example.baseandroidapp.feature.user

import com.example.baseandroidapp.core.model.data.User
import javax.inject.Inject

class UserUIMapper @Inject constructor(
) {
    fun toUserUI(domainUsers: List<User>): List<UserUI> {
        return domainUsers.map { user ->
            UserUI(
                id = user.id,
                name = user.name
            )
        }
    }
}

data class UserUI(
    val id: Int,
    val name: String
)