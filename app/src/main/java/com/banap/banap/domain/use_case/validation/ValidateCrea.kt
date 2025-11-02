package com.banap.banap.domain.use_case.validation

class ValidateCrea {

    private val creaRegex = Regex("^\\d{4,6}-[A-Z]{2}$")

    fun execute (crea: String) : ValidationResult {
        if (crea.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "O CREA não pode estar em branco"
            )
        }

        if (!creaRegex.matches(crea)) {
            return ValidationResult(
                successful = false,
                errorMessage = "O CREA precisa ao menos de 7 numeros e da identificação do estado (RJ, SP, etc...)"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}