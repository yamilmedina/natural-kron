package io.github.yamilmedina.kron.elementprovider.hour


internal open class Base12Hour : Base24Hour() {

    companion object {
        const val PATTERN = "(1[012]|[1-9]):([0-5][0-9])?(?i)\\s?(am|pm)"
    }

    override fun matches(string: String): Boolean {
        val regex = Regex(PATTERN, RegexOption.IGNORE_CASE)
        val matches = regex.find(string)
        this.segments = matches?.groupValues?.toList() ?: emptyList()
        return this.segments.isNotEmpty()
    }

    override fun getHourElement(): String {
        return when {
            segments[3].toLowerCase() == "pm" && segments[1].toInt() < 12 -> (segments[1].toInt() + 12).toString()
            segments[3].toLowerCase() == "am" && segments[1].toInt() == 12 -> 0.toString()
            else -> segments[1]
        }
    }
}
