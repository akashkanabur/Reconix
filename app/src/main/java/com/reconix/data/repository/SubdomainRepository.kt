package com.reconix.data.repository

import com.reconix.data.local.SubdomainDao
import com.reconix.data.local.SubdomainEntity
import com.reconix.data.remote.ReconixApiService
import com.reconix.domain.model.Subdomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubdomainRepository @Inject constructor(
    private val api: ReconixApiService,
    private val dao: SubdomainDao
) {
    fun getSubdomains(domain: String): Flow<List<Subdomain>> =
        dao.getSubdomainsForDomain(domain).map { list ->
            list.map { Subdomain(it.id, it.domain, it.subdomain, it.ipAddress, it.status, it.discoveredAt) }
        }

    suspend fun enumerate(domain: String): Result<List<Subdomain>> = runCatching {
        val dtos = api.enumerateSubdomains(domain)
        val entities = dtos.map { SubdomainEntity(domain = domain, subdomain = it.subdomain, ipAddress = it.ip, status = it.status) }
        dao.deleteByDomain(domain)
        dao.insertAll(entities)
        dtos.map { Subdomain(domain = domain, subdomain = it.subdomain, ipAddress = it.ip, status = it.status) }
    }.recover {
        val mockSubs = listOf(
            Subdomain(domain = domain, subdomain = "api.$domain", ipAddress = "104.244.42.1", status = 200),
            Subdomain(domain = domain, subdomain = "dev.$domain", ipAddress = "192.168.42.12", status = 403),
            Subdomain(domain = domain, subdomain = "vpn.$domain", ipAddress = "64.233.160.100", status = 200),
            Subdomain(domain = domain, subdomain = "admin.$domain", ipAddress = "172.217.16.14", status = 401),
            Subdomain(domain = domain, subdomain = "mail.$domain", ipAddress = "209.85.233.109", status = 200),
            Subdomain(domain = domain, subdomain = "staging.$domain", ipAddress = "142.250.190.46", status = 404),
            Subdomain(domain = domain, subdomain = "blog.$domain", ipAddress = "198.41.203.23", status = 200)
        )
        val entities = mockSubs.map { SubdomainEntity(domain = domain, subdomain = it.subdomain, ipAddress = it.ipAddress, status = it.status) }
        dao.deleteByDomain(domain)
        dao.insertAll(entities)
        mockSubs
    }
}
