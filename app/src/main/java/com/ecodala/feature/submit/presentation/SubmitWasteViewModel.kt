package com.ecodala.feature.submit.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecodala.core.data.repository.LocalEcoRepository
import com.ecodala.core.domain.model.EcoRatingCalculator
import com.ecodala.core.domain.model.WasteType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SubmitWasteUiState(
    val wasteType: WasteType = WasteType.Plastic,
    val quantity: Int = 5,
    val unit: String = "kg",
    val comment: String = "",
    val rewardPoints: Int = EcoRatingCalculator.pointsForWaste(WasteType.Plastic, 5.0, "kg"),
    val isSubmitting: Boolean = false,
    val submitMessage: SubmitWasteMessage? = null,
    val submitMessagePoints: Int = 0
)

enum class SubmitWasteMessage {
    SubmissionFailed,
    Approved,
    SubmittedForReview
}

class SubmitWasteViewModel(
    private val repository: LocalEcoRepository = LocalEcoRepository.shared
) : ViewModel() {
    private val _uiState = MutableStateFlow(SubmitWasteUiState())
    val uiState: StateFlow<SubmitWasteUiState> = _uiState.asStateFlow()

    init { onUnitChange(com.ecodala.core.settings.AppSettingsStore.settings.value.preferredUnit) }

    fun onQuantityChange(value: Int) {
        _uiState.update {
            val quantity = value.coerceIn(1, 10000)
            it.copy(
                quantity = quantity,
                rewardPoints = calculateRewardPoints(it.wasteType, quantity, it.unit)
            )
        }
    }

    fun onWasteTypeChange(value: WasteType) {
        _uiState.update {
            it.copy(
                wasteType = value,
                rewardPoints = calculateRewardPoints(value, it.quantity, it.unit)
            )
        }
    }

    fun onUnitChange(value: String) {
        _uiState.update {
            it.copy(
                unit = value,
                rewardPoints = calculateRewardPoints(it.wasteType, it.quantity, value)
            )
        }
    }

    fun onCommentChange(value: String) {
        _uiState.update { it.copy(comment = value) }
    }

    fun increaseQuantity() {
        _uiState.update {
            val quantity = (it.quantity + 1).coerceAtMost(10000)
            it.copy(
                quantity = quantity,
                rewardPoints = calculateRewardPoints(it.wasteType, quantity, it.unit)
            )
        }
    }

    fun decreaseQuantity() {
        _uiState.update {
            val quantity = (it.quantity - 1).coerceAtLeast(1)
            it.copy(
                quantity = quantity,
                rewardPoints = calculateRewardPoints(it.wasteType, quantity, it.unit)
            )
        }
    }

    fun submitWaste(onSuccess: () -> Unit = {}) {
        val state = _uiState.value
        if (state.isSubmitting) return
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, submitMessage = null) }
            repository.submitWaste(
                type = state.wasteType,
                quantity = state.quantity.toDouble(),
                unit = state.unit,
                comment = state.comment
            )
                .onSuccess { submission ->
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            rewardPoints = submission.earnedPoints.takeIf { points -> points > 0 }
                                ?: calculateRewardPoints(state.wasteType, state.quantity, state.unit),
                            submitMessage = buildSubmitMessage(submission.earnedPoints),
                            submitMessagePoints = submission.earnedPoints
                        )
                    }
                    onSuccess()
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            submitMessage = SubmitWasteMessage.SubmissionFailed,
                            submitMessagePoints = 0
                        )
                    }
                }
        }
    }

    private fun calculateRewardPoints(type: WasteType, quantity: Int, unit: String): Int {
        return EcoRatingCalculator.pointsForWaste(type, quantity.toDouble(), unit)
    }

    private fun buildSubmitMessage(earnedPoints: Int): SubmitWasteMessage {
        return if (earnedPoints > 0) {
            SubmitWasteMessage.Approved
        } else {
            SubmitWasteMessage.SubmittedForReview
        }
    }
}
