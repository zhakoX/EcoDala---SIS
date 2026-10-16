package kz.ecodala.screens.leaderboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.ecodala.data.repository.EcoRepository

class LeaderboardViewModel(
    private val repository: EcoRepository = EcoRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LeaderboardUiState())
    val uiState: StateFlow<LeaderboardUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "LeaderboardViewModel initialized")
        loadLeaderboard()
    }

    fun retry() {
        loadLeaderboard()
    }

    private fun loadLeaderboard() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                status = LeaderboardStatus.LOADING,
                errorMessage = null
            )

            runCatching { repository.loadLeaderboard() }
                .onSuccess { users ->
                    _uiState.value = if (users.isEmpty()) {
                        _uiState.value.copy(
                            status = LeaderboardStatus.EMPTY,
                            users = emptyList(),
                            errorMessage = null
                        )
                    } else {
                        _uiState.value.copy(
                            status = LeaderboardStatus.CONTENT,
                            users = users,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        status = LeaderboardStatus.ERROR,
                        users = emptyList(),
                        errorMessage = throwable.message ?: "Unable to load leaderboard"
                    )
                }
        }
    }

    override fun onCleared() {
        Log.d(TAG, "LeaderboardViewModel.onCleared")
        super.onCleared()
    }

    private companion object {
        const val TAG = "SIS4_VIEWMODEL"
    }
}
