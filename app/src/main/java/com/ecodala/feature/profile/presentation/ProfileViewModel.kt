package com.ecodala.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecodala.core.data.dummy.DummyEcoData
import com.ecodala.core.data.repository.DemoAuthRepository
import com.ecodala.core.data.repository.LocalEcoRepository
import com.ecodala.core.data.repository.DemoPointsEconomyRepository
import com.ecodala.core.domain.model.EcoRatingCalculator
import com.ecodala.core.domain.model.EcoUser
import com.ecodala.core.domain.usecase.GetMonthlyImpactUseCase
import com.ecodala.core.domain.usecase.GetPointsWalletUseCase
import com.ecodala.core.domain.usecase.GetStreakSummaryUseCase
import com.ecodala.core.domain.usecase.LogoutUseCase
import com.ecodala.core.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val thisMonthKg: Double = 0.0,
    val totalPoints: Int = DummyEcoData.currentUser.ecoPoints,
    val thisMonthPoints: Int = 0,
    val trees: Int = 2,
    val streakDays: Int = 0,
    val nextStreakRewardPoints: Int = 0,
    val isEditingProfile: Boolean = false,
    val editFullName: String = "",
    val editEmail: String = "",
    val profileSaved: Boolean = false
) {
    val sustainabilityScore: Int
        get() = (totalPoints.toLong() * 100 / (EcoRatingCalculator.MaxLevel * EcoRatingCalculator.PointsPerLevel))
            .coerceIn(0, 100).toInt()
    val nextMilestoneLevel: Int
        get() = (EcoRatingCalculator.calculate(totalPoints).level + 1).coerceAtMost(EcoRatingCalculator.MaxLevel)
}

class ProfileViewModel(
    private val repository: LocalEcoRepository = LocalEcoRepository.shared
) : ViewModel() {
    private val logoutUseCase = LogoutUseCase(DemoAuthRepository())
    private val pointsRepository = DemoPointsEconomyRepository(repository)
    private val getPointsWalletUseCase = GetPointsWalletUseCase(pointsRepository)
    private val getMonthlyImpactUseCase = GetMonthlyImpactUseCase(pointsRepository)
    private val getStreakSummaryUseCase = GetStreakSummaryUseCase(pointsRepository)

    private val _user = MutableStateFlow(sessionUserOrFallback())
    val user: StateFlow<EcoUser> = _user.asStateFlow()

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            kotlinx.coroutines.flow.combine(repository.data, SessionManager.session) { data, session -> data to session }
                .collect { loadEconomy() }
        }
    }

    fun retry() {
        _user.value = sessionUserOrFallback()
        loadEconomy()
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            logoutUseCase()
            onLoggedOut()
        }
    }

    fun startProfileEdit() {
        val user = _user.value
        _uiState.value = _uiState.value.copy(
            isEditingProfile = true,
            editFullName = user.fullName,
            editEmail = user.email,
            profileSaved = false
        )
    }

    fun cancelProfileEdit() {
        _uiState.value = _uiState.value.copy(
            isEditingProfile = false,
            profileSaved = false
        )
    }

    fun onEditFullNameChange(value: String) {
        _uiState.value = _uiState.value.copy(editFullName = value, profileSaved = false)
    }

    fun onEditEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(editEmail = value, profileSaved = false)
    }

    fun saveProfile() {
        val name = _uiState.value.editFullName.trim().ifBlank { _user.value.fullName }
        val email = _uiState.value.editEmail.trim().ifBlank { _user.value.email }
        viewModelScope.launch {
            SessionManager.updateProfile(fullName = name, email = email)
            _user.value = _user.value.copy(fullName = name, email = email)
            _uiState.value = _uiState.value.copy(
                isEditingProfile = false,
                editFullName = name,
                editEmail = email,
                profileSaved = true
            )
        }
    }

    private fun loadEconomy() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val userResult = repository.currentUser()
            userResult
                .onSuccess { user ->
                    _user.value = user

                }
            val userId = DummyEcoData.currentUser.id
            val wallet = getPointsWalletUseCase(userId).getOrNull()
            val impact = getMonthlyImpactUseCase(userId).getOrNull()
            val streak = getStreakSummaryUseCase(userId).getOrNull()
            val total = wallet?.totalPoints ?: DummyEcoData.currentUser.ecoPoints
            val rating = EcoRatingCalculator.calculate(total)

            _uiState.value = ProfileUiState(
                errorMessage = userResult.exceptionOrNull()?.message,
                thisMonthKg = impact?.recycledKg ?: 0.0,
                totalPoints = total,
                thisMonthPoints = wallet?.thisMonthPoints ?: 0,
                trees = rating.level.coerceAtLeast(1),
                streakDays = streak?.currentDays ?: 0,
                nextStreakRewardPoints = streak?.nextRewardPoints ?: 0,
                editFullName = _user.value.fullName,
                editEmail = _user.value.email
            )
            _user.value = _user.value.copy(
                ecoPoints = total,
                level = rating.level
            )
        }
    }

    private fun sessionUserOrFallback(): EcoUser {
        val session = SessionManager.session.value
        return DummyEcoData.currentUser.copy(
            id = session.userId ?: DummyEcoData.currentUser.id,
            fullName = session.fullName ?: DummyEcoData.currentUser.fullName,
            email = session.email ?: DummyEcoData.currentUser.email
        )
    }
}
