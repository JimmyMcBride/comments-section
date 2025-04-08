package dev.jimmymcbride.comments.domain.repository

import dev.jimmymcbride.comments.domain.models.Comment
import dev.jimmymcbride.comments.domain.utils.AsyncState

interface TypicodeRepository {
    suspend fun getComments(): AsyncState<List<Comment>>
}