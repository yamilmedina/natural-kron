package io.github.yamilmedina.kron

/**
 * Exception thrown when a parsing error occurs.
 */
class KronParsingException(
    override val message: String,
    override val cause: Throwable
) : Exception(message, cause)