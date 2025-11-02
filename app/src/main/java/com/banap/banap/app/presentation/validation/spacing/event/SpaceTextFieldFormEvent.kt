package com.banap.banap.app.presentation.validation.spacing.event

sealed class SpaceTextFieldFormEvent {
    data class SpaceChanged(val space: String) : SpaceTextFieldFormEvent()

    object Submit: SpaceTextFieldFormEvent()
}