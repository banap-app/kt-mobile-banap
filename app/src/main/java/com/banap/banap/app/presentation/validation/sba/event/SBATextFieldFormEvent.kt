package com.banap.banap.app.presentation.validation.sba.event

sealed class SBATextFieldFormEvent {
    data class SBAChanged(val sba: String) : SBATextFieldFormEvent()

    object Submit: SBATextFieldFormEvent()
}