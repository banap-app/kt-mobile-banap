package com.banap.banap.domain.use_case.validation

class ValidateFieldName {

    fun execute (name: String) : ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "O nome não pode estar em branco"
            )
        }

        if (name.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "O nome precisa ter pelo menos 2 caracteres"
            )
        }

        if (name.length >= 40) {
            return ValidationResult(
                successful = false,
                errorMessage = "O nome não pode ter mais de 40 caracteres"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}