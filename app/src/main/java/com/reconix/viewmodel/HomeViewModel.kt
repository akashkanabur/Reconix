package com.reconix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reconix.data.repository.ProgramRepository
import com.reconix.data.repository.VulnerabilityRepository
import com.reconix.domain.model.BountyProgram
import com.reconix.domain.model.Severity
import com.reconix.domain.model.Vulnerability
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val username: String = "Hacker",
    val totalScans: Int = 0,
    val savedTargets: Int = 0,
    val openFindings: Int = 0,
    val criticalFindings: Int = 0,
    val recentVulnerabilities: List<Vulnerability> = emptyList(),
    val trendingPrograms: List<BountyProgram> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val programRepository: ProgramRepository,
    private val vulnerabilityRepository: VulnerabilityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            combine(
                vulnerabilityRepository.getAllVulnerabilities(),
                programRepository.getSavedPrograms()
            ) { vulns, programs ->
                HomeUiState(
                    totalScans = 42,
                    savedTargets = programs.size,
                    openFindings = vulns.count { it.status == com.reconix.domain.model.VulnStatus.OPEN },
                    criticalFindings = vulns.count { it.severity == Severity.CRITICAL },
                    recentVulnerabilities = vulns.take(5),
                    trendingPrograms = programs.take(5),
                    isLoading = false
                )
            }.collect { _uiState.value = it }
        }

        viewModelScope.launch {
            programRepository.searchPrograms(null, null).onSuccess { programs ->
                _uiState.update { it.copy(trendingPrograms = programs.take(5)) }
            }
        }
    }
}
