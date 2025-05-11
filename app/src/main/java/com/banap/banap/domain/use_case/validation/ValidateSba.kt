package com.banap.banap.domain.use_case.validation

class ValidateSba {

    fun execute(sba: String): ValidationResult {
        if (sba.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "O SBA não pode estar em branco"
            )
        }

        val value =
            sba.toDoubleOrNull()
            ?:
            return ValidationResult(
                false,
                "SBA deve ser um número válido"
            )

        if (value < 0 || value > 100) {
            return ValidationResult(false, "SBA precisa estar entre 0 e 100%")
        }

        return ValidationResult(
            successful = true
        )
    }

}