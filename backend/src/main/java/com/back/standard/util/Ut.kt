package com.back.standard.util

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ClaimsBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.io.BufferedReader
import java.io.InputStreamReader
import java.security.Key
import java.util.*
import javax.crypto.SecretKey
import kotlin.concurrent.thread


object Ut {

    object jwt {
        fun toString(secret: String, expireSeconds: Int, body: Map<String, Any>): String {
            val claimsBuilder: ClaimsBuilder = Jwts.claims()
            body.forEach { (key, value) -> claimsBuilder.add(key, value) }

            val issuedAt = Date()
            val expiration = Date(issuedAt.time + 1000L * expireSeconds)

            val secretKey: Key = Keys.hmacShaKeyFor(secret.toByteArray())

            return Jwts.builder()
                .claims(claimsBuilder.build())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(secretKey)
                .compact()
        }

        fun isValid(secret: String, jwtStr: String): Boolean {
            val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
            return try {
                Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwtStr)
                true
            } catch (e: Exception) {
                false
            }
        }

        fun payload(secret: String, jwtStr: String): Map<String, Any>? {
            val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
            return try {
                val claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwtStr)
                    .payload
                claims
            } catch (e: Exception) {
                null
            }
        }
    }

    object json {
        @JvmField
        var objectMapper: ObjectMapper = ObjectMapper()

        @JvmOverloads
        fun toString(obj: Any, defaultValue: String? = null): String? =
            runCatching { objectMapper.writeValueAsString(obj) }.getOrElse { defaultValue ?: "" }
    }

    object cmd {

        fun run(vararg args: String) {
            val isWindows = System.getProperty("os.name").lowercase().contains("win")

            val builder = ProcessBuilder(
                args.map { it.replace("{{DOT_CMD}}", if (isWindows) ".cmd" else "") }
                    .toList()
            )
            builder.redirectErrorStream(true)

            val process = builder.start()

            BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    println(line)
                }
            }

            val exitCode = process.waitFor()
            println("종료 코드: $exitCode")
        }

        fun runAsync(vararg args: String) {
            thread { run(*args) }
        }
    }
}