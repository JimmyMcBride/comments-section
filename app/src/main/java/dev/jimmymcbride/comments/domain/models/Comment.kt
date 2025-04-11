package dev.jimmymcbride.comments.domain.models

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String? = null,
    val email: String? = null,
    val body: String,
    val profileImageUrl: String? = null // ready for future use
)
