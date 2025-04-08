package dev.jimmymcbride.comments.presentation.screens.comments

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jimmymcbride.comments.domain.models.Comment
import dev.jimmymcbride.comments.domain.repository.TypicodeRepository
import dev.jimmymcbride.comments.domain.utils.AsyncState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repo: TypicodeRepository
) : ViewModel() {
    var commentsListState = mutableStateOf<AsyncState<List<Comment>>>(AsyncState.Idle)
        private set

    fun fetchComments() {
        commentsListState.value = AsyncState.Loading
        viewModelScope.launch {
            commentsListState.value = repo.getComments()
        }
    }
}