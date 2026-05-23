package com.reconix.domain.model

data class User(
    val uid: String,
    val username: String,
    val email: String,
    val avatarUrl: String = "",
    val xp: Int = 0,
    val level: Int = 1,
    val isPremium: Boolean = false
)

data class BountyProgram(
    val id: String,
    val name: String,
    val platform: String,
    val domain: String,
    val maxBounty: String,
    val scope: String,
    val isSaved: Boolean = false
)

data class Subdomain(
    val id: Long = 0,
    val domain: String,
    val subdomain: String,
    val ipAddress: String = "",
    val status: Int = 0,
    val discoveredAt: Long = System.currentTimeMillis()
)

data class Vulnerability(
    val id: Long = 0,
    val title: String,
    val target: String,
    val severity: Severity,
    val status: VulnStatus,
    val description: String,
    val notes: String = "",
    val screenshotPath: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class Severity { CRITICAL, HIGH, MEDIUM, LOW, INFO }
enum class VulnStatus { OPEN, IN_PROGRESS, RESOLVED, DUPLICATE, NOT_APPLICABLE }

data class ReconReport(
    val id: Long = 0,
    val title: String,
    val target: String,
    val findings: List<Vulnerability>,
    val createdAt: Long = System.currentTimeMillis()
)

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

enum class NotificationType { BOUNTY_ALERT, PROGRAM_UPDATE, SCAN_COMPLETE, SYSTEM }

data class ScanResult(
    val query: String,
    val results: List<String>,
    val timestamp: Long = System.currentTimeMillis()
)
