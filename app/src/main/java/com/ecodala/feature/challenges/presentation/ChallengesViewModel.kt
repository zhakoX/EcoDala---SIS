package com.ecodala.feature.challenges.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecodala.core.data.dummy.DummyEcoData
import com.ecodala.core.data.repository.LocalEcoRepository
import com.ecodala.core.domain.model.Challenge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChallengesViewModel(
    private val repository: LocalEcoRepository = LocalEcoRepository.shared
) : ViewModel() {
    private val _challenges = MutableStateFlow(DummyEcoData.challenges)
    val challenges: StateFlow<List<Challenge>> = _challenges.asStateFlow()

    init {
        viewModelScope.launch {
            repository.data.collect {
                repository.challenges().onSuccess { _challenges.value = it }
            }
        }
    }
}
