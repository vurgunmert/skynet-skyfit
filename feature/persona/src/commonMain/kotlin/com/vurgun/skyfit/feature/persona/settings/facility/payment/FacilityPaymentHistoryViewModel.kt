package com.vurgun.skyfit.feature.persona.settings.facility.payment

import cafe.adriel.voyager.core.model.ScreenModel
import com.vurgun.skyfit.feature.persona.settings.shared.component.PaymentHistoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FacilityPaymentHistoryViewModel : ScreenModel {

    private val _paymentHistoryItems = MutableStateFlow<List<PaymentHistoryItem>>(emptyList())
    val paymentHistoryItems: StateFlow<List<PaymentHistoryItem>> = _paymentHistoryItems.asStateFlow()

    init {
        loadFakePaymentHistory()
    }

    private fun loadFakePaymentHistory() {
        _paymentHistoryItems.value = listOf(
            PaymentHistoryItem(
                date = "05/02/2025 12:30",
                trainer = "Emma Roberts",
                className = "Yoga",
                cost = "\$220.00",
                paidBy = "Sophia Turner"
            ),
            PaymentHistoryItem(
                date = "10/02/2025 14:15",
                trainer = "John Carter",
                className = "CrossFit",
                cost = "\$180.00",
                paidBy = "Lucas Brown"
            ),
            PaymentHistoryItem(
                date = "15/02/2025 17:00",
                trainer = "Olivia Wilson",
                className = "Zumba",
                cost = "\$150.00",
                paidBy = "Emma Johnson"
            ),
            PaymentHistoryItem(
                date = "20/02/2025 08:45",
                trainer = "Daniel Adams",
                className = "Kickboxing",
                cost = "\$275.00",
                paidBy = "Michael White"
            ),
            PaymentHistoryItem(
                date = "25/02/2025 19:30",
                trainer = "Sophia Martinez",
                className = "HIIT Training",
                cost = "\$320.00",
                paidBy = "David Miller"
            )
        )
    }
}
