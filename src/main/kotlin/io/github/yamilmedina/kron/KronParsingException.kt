package io.github.yamilmedina.kron

enum class FailureType {
    LEXICAL,
    SYNTACTIC,
    SEMANTIC
}

class KronParsingException(
    override val message: String,
    override val cause: Throwable? = null,
    val failureType: FailureType,
    val originalInput: String,
    val reason: String
) : Exception(message, cause) {
    companion object {
        fun lexical(input: String, reason: String, cause: Throwable? = null): KronParsingException =
            KronParsingException(
                message = "Lexical error parsing input [$input]: $reason",
                cause = cause,
                failureType = FailureType.LEXICAL,
                originalInput = input,
                reason = reason
            )

        fun syntactic(input: String, reason: String, cause: Throwable? = null): KronParsingException =
            KronParsingException(
                message = "Syntactic error parsing input [$input]: $reason",
                cause = cause,
                failureType = FailureType.SYNTACTIC,
                originalInput = input,
                reason = reason
            )

        fun semantic(input: String, reason: String, cause: Throwable? = null): KronParsingException =
            KronParsingException(
                message = "Semantic error parsing input [$input]: $reason",
                cause = cause,
                failureType = FailureType.SEMANTIC,
                originalInput = input,
                reason = reason
            )
    }
}
