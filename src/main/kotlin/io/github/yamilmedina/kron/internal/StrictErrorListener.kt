package io.github.yamilmedina.kron.internal

import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer

internal class StrictErrorListener : BaseErrorListener() {
    private var capturedError: String? = null

    override fun syntaxError(
        recognizer: Recognizer<*, *>?,
        offendingSymbol: Any?,
        line: Int,
        charPositionInLine: Int,
        msg: String,
        e: RecognitionException?
    ) {
        if (capturedError == null) {
            capturedError = "line $line:$charPositionInLine $msg"
        }
    }

    fun error(): String? = capturedError
}
