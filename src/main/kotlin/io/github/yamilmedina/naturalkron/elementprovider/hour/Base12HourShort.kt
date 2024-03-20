package  io.github.yamilmedina.naturalkron.elementprovider.hour

import java.util.*
import java.util.regex.Pattern

internal class Base12HourShort : Base12Hour() {

    override fun matches(value: String): Boolean {
        val m = pattern.matcher(value)
        while (m.find()) {
            for (i in 0..m.groupCount()) {
                if (m.group(i) != null) {
                    segments.add(m.group(i))
                }
            }
        }
        return segments.size > 0
    }

    override fun canProvideMinute(): Boolean {
        return segments.size > 1
    }

    override val minuteElement: String
        get() = "0"

    override val hourElement: String?
        get() {
            if (segments.size > 2) {
                if (segments[2].lowercase(Locale.getDefault()) == "pm" && segments[1].toInt() < 12) {
                    return (segments[1].toInt() + 12).toString()
                } else if (segments[2].lowercase(Locale.getDefault()) == "am" && segments[1].toInt() == 12) {
                    return "0"
                }
            }
            return if (segments.size > 1) segments[1] else null
        }

    companion object {
        private const val PATTERN = "(1[012]|[1-9])\\s?(?i)\\s?(am|pm)"
        private val pattern: Pattern = Pattern.compile(PATTERN, Pattern.CASE_INSENSITIVE)
    }
}
