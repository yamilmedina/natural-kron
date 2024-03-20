package  io.github.yamilmedina.naturalkron.elementprovider.hour

import java.util.*

internal class Noon : Midnight() {
    override fun matches(value: String): Boolean {
        match = value != null &&
                (value.lowercase(Locale.getDefault()).indexOf("noon") >= 0 || value.lowercase(Locale.getDefault())
                    .indexOf("midday") >= 0)
        return match
    }

    override val hourElement: String
        get() = "12"
}
