package com.vurgun.skyfit.core.ui.components.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData

@Composable
fun LazySocialPostsColumn(
    posts: List<SocialPostItemViewData>,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(Modifier.height(16.dp))
            SocialQuickPostInputCard(onClickSend = {})
        }

        items(posts) {
            SocialPostCard(
                data = it,
                onClick = {},
                onClickComment = {},
                onClickLike = {},
                onClickShare = {}
            )
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun SocialPostsGrid(posts: List<SocialPostItemViewData>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1), // Mimic a single column list
        modifier = Modifier.fillMaxHeight()
    ) {
        item {
            Spacer(Modifier.height(16.dp))
            SocialQuickPostInputCard(onClickSend = {})
        }

        items(posts) { post ->
            SocialPostCard(
                data = post,
                onClick = {},
                onClickComment = {},
                onClickLike = {},
                onClickShare = {}
            )
        }
    }
}
