package dev.jimmymcbride.comments.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.jimmymcbride.comments.presentation.screens.comments.CommentsScreen
import dev.jimmymcbride.comments.presentation.screens.comments.CommentsViewModel
import dev.jimmymcbride.comments.presentation.ui.theme.CommentsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CommentsTheme {
                val commentsViewModel by viewModels<CommentsViewModel>()
                CommentsScreen(
                    commentsViewModel.commentsListState,
                    commentsViewModel::fetchComments
                )
            }
        }
    }
}
