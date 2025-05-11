package com.banap.banap.domain.use_case.validation

class ValidatePotassium {

    fun execute(potassium: String): ValidationResult {
        if(potassium.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "O potássio não pode estar em branco"
            )
        }

        val value = potassium.toDoubleOrNull()
            ?: return ValidationResult(
                successful = false,
                errorMessage = "O potássio deve ser um número válido"
            )

        if(value < 0) {
            return ValidationResult(
                successful = false,
                errorMessage = "O potássio precisa ser maior que zero"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}