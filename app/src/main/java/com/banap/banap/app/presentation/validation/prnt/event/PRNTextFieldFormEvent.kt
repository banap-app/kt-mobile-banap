package com.banap.banap.app.presentation.validation.prnt.event

sealed class PRNTextFieldFormEvent {
    data class PRNTChanged(val prnt: String) : PRNTextFieldFormEvent()

    object Submit: PRNTextFieldFormEvent()
}