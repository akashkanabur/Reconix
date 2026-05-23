package com.reconix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SubdomainEntity::class, VulnerabilityEntity::class, SavedProgramEntity::class, ReportEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ReconixDatabase : RoomDatabase() {
    abstract fun subdomainDao(): SubdomainDao
    abstract fun vulnerabilityDao(): VulnerabilityDao
    abstract fun savedProgramDao(): SavedProgramDao
    abstract fun reportDao(): ReportDao
}
