/**
 * Created by Dejan Igrec
 */
interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
