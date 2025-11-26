package com.banap.banap.app.presentation.validation.name.event

sealed class NameTextFieldFormEvent {
    data class NameChanged(val name: String) : NameTextFieldFormEvent()
    data class SetError(val errorMessage: String) : NameTextFieldFormEvent()
    data class LoadName(val name: String) : NameTextFieldFormEvent()

    object Submit : NameTextFieldFormEvent()
}

