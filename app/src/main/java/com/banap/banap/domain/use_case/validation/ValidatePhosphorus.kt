package com.banap.banap.domain.use_case.validation

class ValidatePhosphorus {

    fun execute(phosphorus: String): ValidationResult {
        if(phosphorus.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "O fosfóro não pode estar em branco"
            )
        }

        val value = phosphorus.toDoubleOrNull()
            ?: return ValidationResult(
                successful = false,
                errorMessage = "O fosfóro deve ser um número válido"
            )

        if(value < 0) {
            return ValidationResult(
                successful = false,
                errorMessage = "Fosfóro precisa ser maior que zero"
            )
        }

        if(value > 300) {
            return ValidationResult(
                successful = false,
                errorMessage = "Fosfóro acima do valor realista (máx. 300)"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}