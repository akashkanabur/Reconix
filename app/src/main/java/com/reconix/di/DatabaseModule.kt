package com.reconix.di

import android.content.Context
import androidx.room.Room
import com.reconix.data.local.ReconixDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ReconixDatabase =
        Room.databaseBuilder(context, ReconixDatabase::class.java, "reconix.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideSubdomainDao(db: ReconixDatabase) = db.subdomainDao()
    @Provides fun provideVulnerabilityDao(db: ReconixDatabase) = db.vulnerabilityDao()
    @Provides fun provideSavedProgramDao(db: ReconixDatabase) = db.savedProgramDao()
    @Provides fun provideReportDao(db: ReconixDatabase) = db.reportDao()
}
