package com.reconix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reconix.data.repository.ProgramRepository
import com.reconix.domain.model.BountyProgram
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DomainUiState(
    val programs: List<BountyProgram> = emptyList(),
    val savedPrograms: List<BountyProgram> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "",
    val selectedPlatform: String? = null
)

@HiltViewModel
class DomainViewModel @Inject constructor(private val repository: ProgramRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DomainUiState())
    val uiState: StateFlow<DomainUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getSavedPrograms().collect { saved ->
                _uiState.update { it.copy(savedPrograms = saved) }
            }
        }
        search()
    }

    fun onQueryChange(q: String) { _uiState.update { it.copy(query = q) }; search() }
    fun onPlatformFilter(p: String?) { _uiState.update { it.copy(selectedPlatform = p) }; search() }

    fun search() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val state = _uiState.value
            repository.searchPrograms(state.query.ifBlank { null }, state.selectedPlatform)
                .onSuccess { programs -> _uiState.update { it.copy(programs = programs, isLoading = false) } }
                .onFailure { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
        }
    }

    fun toggleSave(program: BountyProgram) {
        viewModelScope.launch {
            if (repository.isSaved(program.id)) repository.unsaveProgram(program)
            else repository.saveProgram(program)
        }
    }
}
