package com.banap.banap.app.presentation.validation.potassium.event

sealed class PotassiumTextFieldFormEvent {
    data class PotassiumChanged(val potassium: String) : PotassiumTextFieldFormEvent()

    object Submit: PotassiumTextFieldFormEvent()
}