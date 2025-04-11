package dev.jimmymcbride.comments.presentation.screens.comments

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import dev.jimmymcbride.comments.R
import dev.jimmymcbride.comments.domain.models.Comment
import dev.jimmymcbride.comments.domain.utils.AsyncState
import dev.jimmymcbride.comments.domain.utils.DuringComposableState
import dev.jimmymcbride.comments.presentation.ui.components.BodyLarge
import dev.jimmymcbride.comments.presentation.ui.components.BodyMedium
import dev.jimmymcbride.comments.presentation.ui.components.CommentCard
import dev.jimmymcbride.comments.presentation.ui.components.LabelLarge
import dev.jimmymcbride.comments.presentation.ui.components.TitleLarge
import dev.jimmymcbride.comments.presentation.ui.theme.CommentsTheme
import dev.jimmymcbride.comments.presentation.ui.theme.LG_LOADING_SIZE
import dev.jimmymcbride.comments.presentation.ui.theme.SM_PADDING
import dev.jimmymcbride.comments.presentation.ui.theme.XSM_PADDING
import dev.jimmymcbride.comments.presentation.ui.theme.avatarBackground

@Composable
fun CommentsScreen(
    commentsListState: State<AsyncState<List<Comment>>>,
    fetchComments: () -> Unit,
) {
    val commentsList by commentsListState

    LaunchedEffect(Unit) {
        fetchComments()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        commentsList.DuringComposableState(success = { commentsList ->
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(SM_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = commentsList) { comment ->
                    CommentItem(comment)
                }
            }
        }, loading = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    Modifier
                        .size(LG_LOADING_SIZE)
                        .testTag("loading_spinner")
                )
            }
        }, error = { message ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleLarge(
                    text = stringResource(R.string.oops),
                )

                BodyLarge(
                    text = message,
                )
            }
        })
    }
}

@Composable
fun CommentItem(comment: Comment) {
    CommentCard {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(SM_PADDING)
        ) {
            // If an when profileImageUrl is available, use it
            if (comment.profileImageUrl != null) {
                AsyncImage(
                    model = comment.profileImageUrl,
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.avatarBackground)
                        .padding(XSM_PADDING)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.default_user),
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.avatarBackground)
                        .padding(XSM_PADDING)
                )
            }
            Spacer(Modifier.size(SM_PADDING))
            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    LabelLarge(
                        text = "Name: ${comment.name}",
                        modifier = Modifier.weight(1f),
                    )
                    LabelLarge(
                        text = "Email: ${comment.email}",
                        modifier = Modifier.weight(1f),
                    )
                }
                LabelLarge(
                    text = "id: ${comment.id}",
                )
                BodyMedium(
                    text = "Body: ${comment.body}",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCommentItem() {
    CommentsTheme {
        CommentItem(
            Comment(
                1,
                2,
                "quo vero reiciendis velit similique earum",
                "Jayne_Kuhic@sydney.com",
                "est natus enim nihil est dolore omnis voluptatem numquam\\net omnis occaecati quod ullam at\\nvoluptatem error expedita pariatur\\nnihil sint nostrum voluptatem reiciendis et"
            )
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewCommentItemDarkMode() {
    CommentsTheme {
        CommentItem(
            Comment(
                1,
                2,
                "quo vero reiciendis velit similique earum",
                "Jayne_Kuhic@sydney.com",
                "est natus enim nihil est dolore omnis voluptatem numquam\\net omnis occaecati quod ullam at\\nvoluptatem error expedita pariatur\\nnihil sint nostrum voluptatem reiciendis et"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommentsScreenSuccessPreview() {
    CommentsTheme {
        CommentsScreen(fakeCommentsListState()) {}
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CommentsScreenSuccessDarkPreview() {
    CommentsTheme {
        CommentsScreen(fakeCommentsListState()) {}
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:width=1230dp,height=300dp,dpi=480"
)
@Composable
fun CommentsScreenSuccessWideDarkPreview() {
    CommentsTheme {
        CommentsScreen(fakeCommentsListState()) {}
    }
}

@Preview(showBackground = true)
@Composable
fun CommentsScreenLoadingPreview() {
    val fakeCommentsListState: MutableState<AsyncState<List<Comment>>> = remember {
        mutableStateOf(AsyncState.Loading)
    }
    CommentsTheme {
        CommentsScreen(fakeCommentsListState) {}
    }
}

@Preview(showBackground = true)
@Composable
fun CommentsScreenErrorPreview() {
    val fakeCommentsListState: MutableState<AsyncState<List<Comment>>> = remember {
        mutableStateOf(AsyncState.Error("there was an error"))
    }
    CommentsTheme {
        CommentsScreen(fakeCommentsListState) {}
    }
}

@Composable
fun fakeCommentsListState(): MutableState<AsyncState<List<Comment>>> {
    return remember {
        mutableStateOf(
            AsyncState.Success(
                listOf(
                    Comment(
                        1,
                        1,
                        "id labore ex et quam laborum",
                        "Eliseo@gardner.biz",
                        "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
                    ),
                    Comment(
                        1,
                        2,
                        "quo vero reiciendis velit similique earum",
                        "Jayne_Kuhic@sydney.com",
                        "est natus enim nihil est dolore omnis voluptatem numquam\\net omnis occaecati quod ullam at\\nvoluptatem error expedita pariatur\\nnihil sint nostrum voluptatem reiciendis et"
                    )
                )
            )
        )
    }
}