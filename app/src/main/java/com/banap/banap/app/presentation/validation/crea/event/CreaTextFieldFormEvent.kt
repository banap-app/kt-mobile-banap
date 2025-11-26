package com.banap.banap.app.presentation.validation.crea.event

sealed class CreaTextFieldFormEvent {
    data class CreaChanged(val crea: String) : CreaTextFieldFormEvent()
    data class SetError(val errorMessage: String) : CreaTextFieldFormEvent()

    object Submit : CreaTextFieldFormEvent()
}