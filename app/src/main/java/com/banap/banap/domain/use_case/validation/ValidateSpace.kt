package com.banap.banap.domain.use_case.validation

class ValidateSpace {

    fun execute(space: String): ValidationResult {
        if (space.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "O campo não pode estar em branco"
            )
        }

        val value = space.toDoubleOrNull()
            ?: return ValidationResult(
                successful = false,
                errorMessage = "O campo deve ser um número válido"
            )

        if(value < 0) {
            return ValidationResult(
                successful = false,
                errorMessage = "O campo precisa ser maior que zero"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}