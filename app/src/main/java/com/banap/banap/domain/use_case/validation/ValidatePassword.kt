package com.banap.banap.domain.use_case.validation

class ValidatePassword {

    fun execute (password: String) : ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "A senha não pode estar em branco"
            )
        }

        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "A senha precisa ter pelo menos 8 caracteres"
            )
        }

        val specialRegex = Regex("[!@#\$%^&*]")

        val containsLetterAndDigits =
                password.any { it.isUpperCase() }
                    &&
                password.any { it.isDigit() }
                    &&
                password.any { it.isLetter() }
                    &&
                password.any { specialRegex.matches(it.toString()) }

        if (!containsLetterAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "A senha precisa ter: letra maiuscula, digito e caractere especial"
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}