package dev.jimmymcbride.comments.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.jimmymcbride.comments.presentation.ui.theme.CARD_MAX_WIDTH
import dev.jimmymcbride.comments.presentation.ui.theme.SM_PADDING

@Composable
fun CommentCard(content: @Composable () -> Unit) {
    Card(
        Modifier
            .padding(SM_PADDING)
            .widthIn(max = CARD_MAX_WIDTH),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        content()
    }
}