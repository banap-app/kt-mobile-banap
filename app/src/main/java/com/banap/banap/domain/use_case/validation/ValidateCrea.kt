package com.banap.banap.domain.use_case.validation

class ValidateCrea {

    private val creaRegex = Regex("^\\d{7,10}$")

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
                errorMessage = "O CREA precisa ao menos de 7 numeros"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}