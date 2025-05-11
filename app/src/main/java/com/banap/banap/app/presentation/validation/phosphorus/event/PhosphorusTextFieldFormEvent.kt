package com.banap.banap.app.presentation.validation.phosphorus.event

sealed class PhosphorusTextFieldFormEvent {
    data class PhosphorusChanged(val phosphorus: String) : PhosphorusTextFieldFormEvent()

    object Submit: PhosphorusTextFieldFormEvent()
}