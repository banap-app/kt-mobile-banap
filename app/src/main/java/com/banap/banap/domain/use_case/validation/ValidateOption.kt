package com.banap.banap.domain.use_case.validation

class ValidateOption {

    fun execute (option: String) : ValidationResult {
        if (option.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Escolha uma das opções disponiveis"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}