package com.ecodala.feature.achievements.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecodala.core.data.repository.LocalEcoRepository
import com.ecodala.core.domain.model.Achievement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AchievementsViewModel(
    private val repository: LocalEcoRepository = LocalEcoRepository.shared
) : ViewModel() {
    private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
    val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()

    init {
        viewModelScope.launch { repository.data.collect { loadAchievements() } }
    }

    private fun loadAchievements() {
        viewModelScope.launch {
            repository.achievements().onSuccess { _achievements.value = it }
        }
    }
}
