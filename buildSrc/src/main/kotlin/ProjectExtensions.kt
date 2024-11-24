import java.util.*
import org.gradle.api.Project

/**
 * Convenience method to obtain a property from `$projectRoot/local.properties` file
 * without passing the project param
 */
fun <T> Project.getLocalProperty(propertyName: String, defaultValue: T): T {
    return getLocalProperty(propertyName, defaultValue, this)
}

/**
 * Util to obtain property declared on `$projectRoot/local.properties` file or default
 */
@Suppress("UNCHECKED_CAST")
internal fun <T> getLocalProperty(propertyName: String, defaultValue: T, project: Project): T {
    val localProperties = Properties().apply {
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            load(localPropertiesFile.inputStream())
        }
    }

    val localValue = localProperties.getOrDefault(propertyName, defaultValue) as? T ?: defaultValue
    if (localValue != null) {
        println("> Reading local prop '$propertyName'")
    }
    return localValue
}

/**
 * Try to get an environment variable, if not found, try to get a local property, if not found, return default value
 * Useful for CI/CD pipelines and local development environments.
 */
fun Project.getEnvOrLocalProperty(propertyName: String, defaultValue: String): String {
    return System.getenv(propertyName) ?: getLocalProperty(propertyName, defaultValue)
}