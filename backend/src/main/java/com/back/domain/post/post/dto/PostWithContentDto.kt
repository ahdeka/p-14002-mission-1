package com.back.domain.post.post.dto

import com.back.domain.post.post.entity.Post
import java.time.LocalDateTime

data class PostWithContentDto(
    val id: Int,
    val createDate: LocalDateTime?,
    val modifyDate: LocalDateTime?,
    val authorId: Int,
    val authorName: String?,
    val title: String?,
    val content: String?
) {
    constructor(post: Post) : this(
        id = post.id,
        createDate = post.createDate,
        modifyDate = post.modifyDate,
        authorId = post.getAuthor().id,
        authorName = post.getAuthor().nickname,
        title = post.getTitle(),
        content = post.getContent()
    )
}