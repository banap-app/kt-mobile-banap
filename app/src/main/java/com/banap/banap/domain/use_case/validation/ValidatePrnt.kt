package com.banap.banap.domain.use_case.validation

class ValidatePrnt {

    fun execute(prnt: String): ValidationResult {
        if (prnt.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "O PRNT não pode estar em branco"
            )
        }

        val value =
            prnt.toDoubleOrNull()
                ?:
                return ValidationResult(
                    false,
                    "PRNT deve ser um número válido"
                )

        if (value < 0 || value > 100) {
            return ValidationResult(false, "PRNT precisa estar entre 0 e 100%")
        }

        return ValidationResult(
            successful = true
        )
    }

}