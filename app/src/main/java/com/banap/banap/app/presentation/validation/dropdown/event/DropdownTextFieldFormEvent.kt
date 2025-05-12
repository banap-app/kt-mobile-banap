package com.banap.banap.app.presentation.validation.dropdown.event

sealed class DropdownTextFieldFormEvent {
    data class OptionChanged(val option: String) : DropdownTextFieldFormEvent()

    object Submit : DropdownTextFieldFormEvent()
}