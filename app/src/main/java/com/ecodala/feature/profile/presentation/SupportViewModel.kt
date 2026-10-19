package com.ecodala.feature.profile.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SupportUiState(
    val message: String = "",
    val selectedTopic: String = "Recycling point issue",
    val topics: List<String> = listOf(
        "Recycling point issue",
        "Waste submission",
        "Eco points",
        "Account and profile",
        "Recycling history"
    ),
    val contacts: List<SupportContact> = listOf(
        SupportContact("Demo support", "Local demonstration", "Messages stay on this screen"),
        SupportContact("Demo help", "Ask during the defense", "No external service"),
        SupportContact("Demo email", "support@example.invalid", "Illustrative contact only")
    ),
    val faqs: List<SupportFaq> = listOf(
        SupportFaq("How are EcoPoints counted?", "Points are added after a confirmed waste submission or completed challenge."),
        SupportFaq("Can I suggest a new recycling point?", "Yes. Send the address, photo and accepted waste types through support."),
        SupportFaq("Which cities are supported?", "The demo flow is prepared for Kazakhstan, starting with Almaty and Astana.")
    ),
    val submitted: Boolean = false
)

data class SupportContact(
    val title: String,
    val value: String,
    val subtitle: String
)

data class SupportFaq(
    val question: String,
    val answer: String
)

class SupportViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SupportUiState())
    val uiState: StateFlow<SupportUiState> = _uiState.asStateFlow()

    fun selectTopic(topic: String) {
        _uiState.update { it.copy(selectedTopic = topic, submitted = false) }
    }

    fun updateMessage(message: String) {
        _uiState.update { it.copy(message = message, submitted = false) }
    }

    fun submitRequest() {
        _uiState.update { it.copy(message = "", submitted = true) }
    }
}
