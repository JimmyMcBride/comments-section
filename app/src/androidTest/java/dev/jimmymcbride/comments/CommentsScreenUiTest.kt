package dev.jimmymcbride.comments

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.jimmymcbride.comments.domain.models.Comment
import dev.jimmymcbride.comments.domain.utils.AsyncState
import dev.jimmymcbride.comments.presentation.screens.comments.CommentsScreen
import dev.jimmymcbride.comments.presentation.ui.theme.CommentsTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CommentsScreenUiTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showsLoadingIndicator_whenStateIsLoading() {
        val state = mutableStateOf<AsyncState<List<Comment>>>(AsyncState.Loading)

        composeTestRule.setContent {
            CommentsTheme {
                CommentsScreen(state) {}
            }
        }

        composeTestRule
            .onNode(hasTestTag("loading_spinner"))
            .assertIsDisplayed()
    }

    @Test
    fun showsErrorMessage_whenStateIsError() {
        val errorMessage = "Something went wrong"
        val state = mutableStateOf<AsyncState<List<Comment>>>(AsyncState.Error(errorMessage))

        composeTestRule.setContent {
            CommentsTheme {
                CommentsScreen(state) {}
            }
        }

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }

    @Test
    fun showsCommentList_whenStateIsSuccess() {
        val state = mutableStateOf<AsyncState<List<Comment>>>(
            AsyncState.Success(
                listOf(
                    Comment(1, 1, "Name", "email@example.com", "Comment body")
                )
            )
        )

        composeTestRule.setContent {
            CommentsTheme {
                CommentsScreen(state) {}
            }
        }

        composeTestRule
            .onNodeWithText("Name: Name")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Email: email@example.com")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Body: Comment body")
            .assertIsDisplayed()
    }
}