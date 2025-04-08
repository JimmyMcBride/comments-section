package dev.jimmymcbride.comments

import dev.jimmymcbride.comments.TestData.mockList
import dev.jimmymcbride.comments.data.remote.ApiService
import dev.jimmymcbride.comments.data.repository.TypicodeRepositoryImpl
import dev.jimmymcbride.comments.domain.models.Comment
import dev.jimmymcbride.comments.domain.repository.TypicodeRepository
import dev.jimmymcbride.comments.domain.utils.AsyncState
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class TypicodeRepositoryTest {
    private lateinit var apiService: ApiService
    private lateinit var repository: TypicodeRepository

    @Before
    fun setUp() {
        apiService = mock(ApiService::class.java) // Mock the API service
        repository = TypicodeRepositoryImpl(apiService) // Pass mock to the repository
    }

    @Test
    fun `getComments returns Success when API call is successful`() = runTest {
        // Mock a successful response with a Page object
        val mockResponse = Response.success(mockList)
        `when`(apiService.getComments()).thenReturn(mockResponse)

        // Call the repository function
        val result = repository.getComments()

        // Assert the result is Success
        assert(result is AsyncState.Success)
        assertEquals(mockList, (result as AsyncState.Success).data)
    }

    @Test
    fun `getComments returns Error when API call is unsuccessful`() = runTest {
        // Mock an unsuccessful response (e.g., 400 error)
        val mockResponse = Response.error<List<Comment>>(400, mockResponseBody())
        `when`(apiService.getComments()).thenReturn(mockResponse)

        // Call the repository function
        val result = repository.getComments()

        // Assert the result is Error
        assert(result is AsyncState.Error)
        assertEquals("Something went wrong.", (result as AsyncState.Error).message)
    }

    @Test
    fun `getHomePageFeeds returns Error when exception is thrown`() = runTest {
        // Mock an exception being thrown from the API call
        `when`(apiService.getComments()).thenThrow(RuntimeException("Network error"))

        // Call the repository function
        val result = repository.getComments()

        // Assert the result is Error with the correct message
        assert(result is AsyncState.Error)
        assertEquals("Network error", (result as AsyncState.Error).message)
    }

    // Helper method to mock response body for error case
    private fun mockResponseBody(): okhttp3.ResponseBody {
        return okhttp3.ResponseBody.create(
            "application/json".toMediaTypeOrNull(),
            "{}" // Mock empty JSON response
        )
    }
}