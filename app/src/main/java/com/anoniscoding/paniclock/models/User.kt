package com.anoniscoding.paniclock.models

data class User (
    var isOnboarded: Boolean = false,
    var safePin: String = "",
    var panicPin: String = ""
) {
    companion object {
        fun empty(): User {
            return User(false, "", "")
        }
    }
}