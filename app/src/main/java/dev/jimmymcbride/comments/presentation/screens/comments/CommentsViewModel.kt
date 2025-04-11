package dev.jimmymcbride.comments.presentation.screens.comments

import androidx.compose.runtime.State
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
    private val _commentsListState = mutableStateOf<AsyncState<List<Comment>>>(AsyncState.Idle)
    val commentsListState: State<AsyncState<List<Comment>>> = _commentsListState

    fun fetchComments() {
        _commentsListState.value = AsyncState.Loading
        viewModelScope.launch {
            _commentsListState.value = repo.getComments()
        }
    }
}