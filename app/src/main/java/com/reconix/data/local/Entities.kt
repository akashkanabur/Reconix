package com.reconix.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reconix.domain.model.Severity
import com.reconix.domain.model.VulnStatus

@Entity(tableName = "subdomains")
data class SubdomainEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val domain: String,
    val subdomain: String,
    val ipAddress: String = "",
    val status: Int = 0,
    val discoveredAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "vulnerabilities")
data class VulnerabilityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val target: String,
    val severity: String,
    val status: String,
    val description: String,
    val notes: String = "",
    val screenshotPath: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_programs")
data class SavedProgramEntity(
    @PrimaryKey val id: String,
    val name: String,
    val platform: String,
    val domain: String,
    val maxBounty: String,
    val scope: String
)

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val target: String,
    val findingsJson: String,
    val createdAt: Long = System.currentTimeMillis()
)
