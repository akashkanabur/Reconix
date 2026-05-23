package com.reconix.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

data class ProgramDto(
    val id: String = "",
    val name: String = "",
    val platform: String = "",
    val domain: String = "",
    val max_bounty: String = "",
    val scope: String = ""
)

data class SubdomainDto(
    val subdomain: String = "",
    val ip: String = "",
    val status: Int = 0
)

data class WhoisDto(
    val domain: String = "",
    val registrar: String = "",
    val created: String = "",
    val expires: String = "",
    val nameservers: List<String> = emptyList()
)

data class DnsDto(
    val domain: String = "",
    val records: Map<String, List<String>> = emptyMap()
)

data class SslDto(
    val domain: String = "",
    val issuer: String = "",
    val validFrom: String = "",
    val validTo: String = "",
    val isValid: Boolean = false
)

interface ReconixApiService {
    @GET("programs")
    suspend fun getBountyPrograms(
        @Query("platform") platform: String? = null,
        @Query("q") query: String? = null
    ): List<ProgramDto>

    @GET("subdomains")
    suspend fun enumerateSubdomains(@Query("domain") domain: String): List<SubdomainDto>

    @GET("whois")
    suspend fun whoisLookup(@Query("domain") domain: String): WhoisDto

    @GET("dns")
    suspend fun dnsLookup(@Query("domain") domain: String): DnsDto

    @GET("headers")
    suspend fun httpHeaders(@Query("url") url: String): Map<String, String>

    @GET("ssl")
    suspend fun sslCheck(@Query("domain") domain: String): SslDto

    @GET("tech")
    suspend fun detectTech(@Query("url") url: String): List<String>

    @GET("ports")
    suspend fun scanPorts(@Query("host") host: String): List<Int>
}
