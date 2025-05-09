package com.banap.banap.app.presentation.validation.ctc.event

import com.banap.banap.app.presentation.validation.prnt.event.PRNTextFieldFormEvent

sealed class CTCTextFieldFormEvent {
    data class CTChanged(val ctc: String) : CTCTextFieldFormEvent()

    object Submit: CTCTextFieldFormEvent()
}