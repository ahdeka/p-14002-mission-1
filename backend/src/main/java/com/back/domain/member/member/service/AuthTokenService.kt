package com.back.domain.member.member.service

import com.back.domain.member.member.entity.Member
import com.back.standard.util.Ut
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Map

@Service
class AuthTokenService {
    @Value("\${custom.jwt.secretKey}")
    private lateinit var jwtSecretKey: String

    @Value("\${custom.accessToken.expirationSeconds}")
    private val accessTokenExpirationSeconds = 0

    fun genAccessToken(member: Member): String {
        val id = member.id
        val username = member.username
        val name = member.nickname

        return Ut.jwt.toString(
            jwtSecretKey,
            accessTokenExpirationSeconds,
            mapOf<String, Any>("id" to id!!, "username" to username!!, "name" to name!!)
        )
    }

    fun payload(accessToken: String?): Map<String, Any>? {
        if (accessToken == null) return null
        return Ut.jwt.payload(jwtSecretKey, accessToken) as? Map<String, Any>
    }
}