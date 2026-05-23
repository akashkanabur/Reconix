package com.reconix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reconix.data.repository.SubdomainRepository
import com.reconix.domain.model.Subdomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SubdomainUiState(
    val domain: String = "",
    val subdomains: List<Subdomain> = emptyList(),
    val scanLogs: List<String> = emptyList(),
    val isScanning: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class SubdomainViewModel @Inject constructor(private val repository: SubdomainRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SubdomainUiState())
    val uiState: StateFlow<SubdomainUiState> = _uiState.asStateFlow()

    fun onDomainChange(domain: String) = _uiState.update { it.copy(domain = domain) }

    fun startScan() {
        val domain = _uiState.value.domain.trim()
        if (domain.isBlank()) return
        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true, scanLogs = listOf("Initializing scan for $domain..."), error = null) }
            addLog("Resolving DNS records...")
            addLog("Querying certificate transparency logs...")
            addLog("Brute-forcing common subdomains...")
            repository.enumerate(domain)
                .onSuccess { subs ->
                    addLog("Found ${subs.size} subdomains.")
                    _uiState.update { it.copy(subdomains = subs, isScanning = false) }
                    repository.getSubdomains(domain).collect { cached ->
                        _uiState.update { it.copy(subdomains = cached) }
                    }
                }
                .onFailure { e ->
                    addLog("Error: ${e.message}")
                    _uiState.update { it.copy(error = e.message, isScanning = false) }
                }
        }
    }

    private fun addLog(msg: String) = _uiState.update { it.copy(scanLogs = it.scanLogs + msg) }
}
