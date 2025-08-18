package com.back.domain.member.member.entity

import com.back.global.jpa.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*

@Entity
class Member() : BaseEntity()
{
    @Column(unique = true)
    var username: String? = null
        private set

    var password: String? = null
        private set

    var nickname: String? = null
        private set

    @Column(unique = true)
    var apiKey: String? = null
        private set

    var profileImgUrl: String? = null
        private set

    constructor(id: Int, username: String?, nickname: String?) : this() {
        this.id = id
        this.username = username
        this.nickname = nickname
    }

    constructor(username: String?, password: String?, nickname: String?, profileImgUrl: String?) : this() {
        this.username = username
        this.password = password
        this.nickname = nickname
        this.profileImgUrl = profileImgUrl
        this.apiKey = UUID.randomUUID().toString()
    }

    fun modifyApiKey(apiKey: String?) {
        this.apiKey = apiKey
    }

    val isAdmin: Boolean
        get() = username == "system" || username == "admin"

    val authorities: List<GrantedAuthority>
        get() = authoritiesAsStringList.map { role -> SimpleGrantedAuthority(role) }

    private val authoritiesAsStringList: List<String>
        get() {
            val authorities = mutableListOf<String>()
            if (isAdmin) authorities.add("ROLE_ADMIN")
            return authorities
        }

    fun modify(nickname: String?, profileImgUrl: String?) {
        this.nickname = nickname
        this.profileImgUrl = profileImgUrl
    }

    val profileImgUrlOrDefault: String
        get() = profileImgUrl ?: "https://placehold.co/600x600?text=U_U"
}
