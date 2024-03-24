package io.github.yamilmedina.kron.elementprovider.hour

open class Base12HourShort : Base12Hour() {

    companion object {
        const val PATTERN = "(1[012]|[1-9])\\s?(?i)\\s?(am|pm)"
    }

    override fun matches(string: String): Boolean {
        val regex = Regex(PATTERN, RegexOption.IGNORE_CASE)
        val matches = regex.find(string)
        this.segments = matches?.groupValues?.toList() ?: emptyList()
        return this.segments.isNotEmpty()
    }

    override fun getHourElement(): String {
        return when {
            segments[2].toLowerCase() == "pm" && segments[1].toInt() < 12 -> (segments[1].toInt() + 12).toString()
            segments[2].toLowerCase() == "am" && segments[1].toInt() == 12 -> 0.toString()
            else -> segments[1]
        }
    }

    override fun canProvideMinute(): Boolean {
        return segments.size > 1
    }

    override fun getMinuteElement(): String {
        return 0.toString()
    }
}


