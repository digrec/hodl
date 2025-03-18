import org.gradle.api.GradleException
import org.gradle.api.logging.Logger

/**
 * Validates version name and calculates version code from it.
 *
 * @param versionName The version name in format X.Y.Z
 * @param logger Logger for output
 * @return The calculated version code
 * @throws GradleException if version name is invalid or code calculation fails
 */
fun calculateVersionCode(versionName: String, logger: Logger): Int {
    // Validate version name
    logger.lifecycle("Validating version name '$versionName'")
    if (versionName.isEmpty()) {
        throw GradleException("Version name '$versionName' must not be an empty string")
    }

    val versionPattern = """^(\d+)\.(\d+)\.(\d+)$""".toRegex()
    val matchResult = versionPattern.find(versionName)
    if (matchResult == null) {
        throw GradleException("Version name '$versionName' is not in the required format (X.Y.Z)")
    }

    // Parse version components
    val (_, majorStr, minorStr, patchStr) = matchResult.groupValues
    val major = majorStr.toInt()
    val minor = minorStr.toInt()
    val patch = patchStr.toInt()

    // Calculate version code
    val versionCode = major * 10000 + minor * 100 + patch
    logger.lifecycle("  • Version code: $versionCode")

    if (versionCode < 10000) {
        throw GradleException("Failed to calculate versionCode")
    }

    return versionCode
}
