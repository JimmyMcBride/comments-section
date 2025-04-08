package dev.jimmymcbride.comments

import dev.jimmymcbride.comments.TestData.mockList
import dev.jimmymcbride.comments.domain.repository.TypicodeRepository
import dev.jimmymcbride.comments.domain.utils.AsyncState
import dev.jimmymcbride.comments.presentation.screens.comments.CommentsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CommentsViewModelTest {
    private lateinit var mockRepository: TypicodeRepository
    private lateinit var viewModel: CommentsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        mockRepository = mock(TypicodeRepository::class.java)
        viewModel = CommentsViewModel(mockRepository)
    }

    // Clean up the Main dispatcher after each test
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `loadHomePageFeeds sets Loading and then Success state`() = runTest {
        // Mock repository to return a successful response

        whenever(mockRepository.getComments()).thenReturn(AsyncState.Success(mockList))

        // Initially, state should be Idle
        assertEquals(AsyncState.Idle, viewModel.commentsListState.value)

        // Trigger loading
        viewModel.fetchComments()

        // Verify that the state is set to Loading
        assertEquals(AsyncState.Loading, viewModel.commentsListState.value)

        // Advance the coroutine until idle
        advanceUntilIdle()

        // After the coroutine, the state should be Success with the mock data
        assertEquals(AsyncState.Success(mockList), viewModel.commentsListState.value)
    }

    @Test
    fun `loadHomePageFeeds sets Loading and then Error state`() = runTest {
        // Mock repository to return an error
        val errorMessage = "Error occurred"
        whenever(mockRepository.getComments()).thenReturn(AsyncState.Error(errorMessage))

        // Trigger loading
        viewModel.fetchComments()

        // Verify that the state is set to Loading
        assertEquals(AsyncState.Loading, viewModel.commentsListState.value)

        // Advance the coroutine until idle
        advanceUntilIdle()

        // After the coroutine, the state should be Error
        assertEquals(AsyncState.Error(errorMessage), viewModel.commentsListState.value)
    }
}