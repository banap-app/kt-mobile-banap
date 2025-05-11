package com.banap.banap.domain.use_case.validation

class ValidateCtc {

    fun execute(ctc: String): ValidationResult {
        if (ctc.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "O CTC não pode estar em branco"
            )
        }

        val value =
            ctc.toDoubleOrNull()
            ?:
            return ValidationResult(
                false,
                "CTC deve ser um número válido"
            )

        if (value <= 0) {
            return ValidationResult(
                false,
                "CTC precisa ser maior que zero"
            )
        }

        if (value > 200) {
            return ValidationResult(
                false,
                "CTC acima do valor realista (máx. 200)"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}