package dev.jimmymcbride.comments.domain.models

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String,
    val profileImageUrl: String? = null // ready for future use
)
