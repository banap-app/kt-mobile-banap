package com.banap.banap.app.presentation.validation.model

data class RegistrationFormState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val crea: String = "",
    val creaError: String? = null,
    val description: String = "",
    val descriptionError: String? = null,
    val option: String = "",
    val optionError: String? = null,
    val optionPriority: String = "",
    val optionPriorityError: String? = null,
    val prnt: String = "",
    val prntError: String? = null,
    val sba: String = "",
    val sbaError: String? = null,
    val ctc: String = "",
    val ctcError: String? = null,
    val phosphorus: String = "",
    val phosphorusError: String? = null,
    val potassium: String = "",
    val potassiumError: String? = null
)
