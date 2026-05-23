package com.reconix.data.repository

import com.reconix.data.local.SavedProgramDao
import com.reconix.data.local.SavedProgramEntity
import com.reconix.data.remote.ReconixApiService
import com.reconix.domain.model.BountyProgram
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramRepository @Inject constructor(
    private val api: ReconixApiService,
    private val dao: SavedProgramDao
) {
    private val mockPrograms = listOf(
        BountyProgram("1", "Google VRP", "HackerOne", "*.google.com", "$31,337", "Web, Android, iOS"),
        BountyProgram("2", "Meta Bug Bounty", "Bugcrowd", "*.facebook.com", "$45,000", "Web, API, VR"),
        BountyProgram("3", "Tesla VRP", "Bugcrowd", "*.tesla.com", "$15,000", "Car Firmware, Web, API"),
        BountyProgram("4", "GitHub Bounty", "HackerOne", "*.github.com", "$30,000", "Web, API"),
        BountyProgram("5", "Netflix Program", "Bugcrowd", "*.netflix.com", "$20,000", "Web, Streaming API"),
        BountyProgram("6", "Slack VRP", "HackerOne", "*.slack.com", "$15,000", "Desktop, Web, API"),
        BountyProgram("7", "Uber Security", "HackerOne", "*.uber.com", "$10,000", "Web, Mobile Apps"),
        BountyProgram("8", "Airbnb Bounty", "HackerOne", "*.airbnb.com", "$15,000", "Web, API, Mobile"),
        BountyProgram("9", "TikTok VRP", "HackerOne", "*.tiktok.com", "$12,000", "Mobile Apps, API"),
        BountyProgram("10", "Stripe Security", "Bugcrowd", "*.stripe.com", "$25,000", "Web, API")
    )

    suspend fun searchPrograms(query: String?, platform: String?): Result<List<BountyProgram>> =
        runCatching {
            api.getBountyPrograms(platform = platform, query = query).map {
                BountyProgram(it.id, it.name, it.platform, it.domain, it.max_bounty, it.scope)
            }
        }.recover {
            mockPrograms.filter { program ->
                val matchesQuery = query == null || 
                    program.name.contains(query, ignoreCase = true) || 
                    program.domain.contains(query, ignoreCase = true)
                val matchesPlatform = platform == null || 
                    program.platform.equals(platform, ignoreCase = true)
                matchesQuery && matchesPlatform
            }
        }

    fun getSavedPrograms(): Flow<List<BountyProgram>> =
        dao.getAllSavedPrograms().map { list ->
            list.map { BountyProgram(it.id, it.name, it.platform, it.domain, it.maxBounty, it.scope, isSaved = true) }
        }

    suspend fun saveProgram(program: BountyProgram) =
        dao.insert(SavedProgramEntity(program.id, program.name, program.platform, program.domain, program.maxBounty, program.scope))

    suspend fun unsaveProgram(program: BountyProgram) =
        dao.delete(SavedProgramEntity(program.id, program.name, program.platform, program.domain, program.maxBounty, program.scope))

    suspend fun isSaved(id: String) = dao.isSaved(id)
}
