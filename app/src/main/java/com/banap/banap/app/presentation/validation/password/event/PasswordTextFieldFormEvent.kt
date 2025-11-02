package com.banap.banap.app.presentation.validation.password.event

sealed class PasswordTextFieldFormEvent {
    data class PasswordChanged(val password: String) : PasswordTextFieldFormEvent()
    data class SetError(val errorMessage: String) : PasswordTextFieldFormEvent()
    data class LoadPassword(val password: String) : PasswordTextFieldFormEvent()

    object Submit : PasswordTextFieldFormEvent()
}