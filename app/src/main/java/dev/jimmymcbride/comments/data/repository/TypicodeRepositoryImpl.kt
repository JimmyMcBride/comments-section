package dev.jimmymcbride.comments.data.repository

import dev.jimmymcbride.comments.data.remote.ApiService
import dev.jimmymcbride.comments.domain.models.Comment
import dev.jimmymcbride.comments.domain.repository.TypicodeRepository
import dev.jimmymcbride.comments.domain.utils.AsyncState

class TypicodeRepositoryImpl(
    private val apiService: ApiService
) : TypicodeRepository {
    override suspend fun getComments(): AsyncState<List<Comment>> {
        return try {
            val res = apiService.getComments()
            val body = res.body()
            if (res.isSuccessful && body != null)
                AsyncState.Success(data = body)
            else
                AsyncState.Error(message = "Something went wrong.")
        } catch (e: Exception) {
            AsyncState.Error(message = e.message.toString())
        }
    }
}