package com.reconix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reconix.data.repository.ReconToolsRepository
import com.reconix.domain.model.ScanResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ToolUiState(
    val query: String = "",
    val result: ScanResult? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ReconToolsViewModel @Inject constructor(private val repository: ReconToolsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ToolUiState())
    val uiState: StateFlow<ToolUiState> = _uiState.asStateFlow()

    fun onQueryChange(q: String) = _uiState.update { it.copy(query = q) }

    fun runWhois() = runTool { repository.whoisLookup(it) }
    fun runDns() = runTool { repository.dnsLookup(it) }
    fun runHttpHeaders() = runTool { repository.httpHeaders(it) }
    fun runSsl() = runTool { repository.sslCheck(it) }
    fun runTechDetect() = runTool { repository.detectTech(it) }
    fun runPortScan() = runTool { repository.scanPorts(it) }

    private fun runTool(block: suspend (String) -> Result<ScanResult>) {
        val query = _uiState.value.query.trim()
        if (query.isBlank()) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, result = null) }
            block(query)
                .onSuccess { result -> _uiState.update { it.copy(result = result, isLoading = false) } }
                .onFailure { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
        }
    }

    fun reset() = _uiState.update { ToolUiState() }
}
