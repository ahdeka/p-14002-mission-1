package com.back.domain.post.postComment.entity

import com.back.domain.member.member.entity.Member
import com.back.domain.post.post.entity.Post
import com.back.global.exception.ServiceException
import com.back.global.jpa.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
class PostComment(
    @field:ManyToOne
    private var author: Member,
    @field:ManyToOne
    private var post: Post,  // null 허용하지 않도록 변경
    private var content: String?
) : BaseEntity() {

    // Getter 메서드들
    fun getAuthor(): Member = author
    fun getPost(): Post = post
    fun getContent(): String? = content

    fun modify(content: String?) {
        this.content = content
    }

    fun checkActorCanModify(actor: Member?) {
        if (author != actor) {
            throw ServiceException("403-1", "${id}번 댓글 수정권한이 없습니다.")
        }
    }

    fun checkActorCanDelete(actor: Member?) {
        if (author != actor) {
            throw ServiceException("403-2", "${id}번 댓글 삭제권한이 없습니다.")
        }
    }
}