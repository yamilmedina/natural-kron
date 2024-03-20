package  io.github.yamilmedina.naturalkron

data class KronParserException(override val message: String) : IllegalArgumentException(message)