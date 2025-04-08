package dev.jimmymcbride.comments.data.remote

import dev.jimmymcbride.comments.domain.models.Comment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts/{postId}/comments")
    suspend fun getComments(
        @Path("postId") postId: Int = 1
    ): Response<List<Comment>>
}