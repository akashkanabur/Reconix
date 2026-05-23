package com.reconix.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubdomainDao {
    @Query("SELECT * FROM subdomains WHERE domain = :domain ORDER BY discoveredAt DESC")
    fun getSubdomainsForDomain(domain: String): Flow<List<SubdomainEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(subdomains: List<SubdomainEntity>)

    @Query("DELETE FROM subdomains WHERE domain = :domain")
    suspend fun deleteByDomain(domain: String)
}

@Dao
interface VulnerabilityDao {
    @Query("SELECT * FROM vulnerabilities ORDER BY createdAt DESC")
    fun getAllVulnerabilities(): Flow<List<VulnerabilityEntity>>

    @Query("SELECT * FROM vulnerabilities WHERE severity = :severity ORDER BY createdAt DESC")
    fun getVulnerabilitiesBySeverity(severity: String): Flow<List<VulnerabilityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vulnerability: VulnerabilityEntity): Long

    @Update
    suspend fun update(vulnerability: VulnerabilityEntity)

    @Delete
    suspend fun delete(vulnerability: VulnerabilityEntity)
}

@Dao
interface SavedProgramDao {
    @Query("SELECT * FROM saved_programs")
    fun getAllSavedPrograms(): Flow<List<SavedProgramEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(program: SavedProgramEntity)

    @Delete
    suspend fun delete(program: SavedProgramEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_programs WHERE id = :id)")
    suspend fun isSaved(id: String): Boolean
}

@Dao
interface ReportDao {
    @Query("SELECT * FROM reports ORDER BY createdAt DESC")
    fun getAllReports(): Flow<List<ReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: ReportEntity): Long

    @Delete
    suspend fun delete(report: ReportEntity)
}
