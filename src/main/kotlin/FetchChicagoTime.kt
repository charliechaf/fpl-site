import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.js.Date

@Serializable
data class TimeResponse(val datetime: String? = null)

suspend fun fetchChicagoTime(): String {
    return try {
        val response = fetch("https://worldtimeapi.org/api/timezone/America/Chicago").await()
        val jsonText = response.text().await()
        val timeData = Json { ignoreUnknownKeys = true }.decodeFromString<TimeResponse>(jsonText)
        
        timeData.datetime?.let { datetime ->
            Date(datetime).toLocaleString("en-US", js("{ timeZone: 'America/Chicago' }"))
        } ?: ""
    } catch (e: Exception) {
        "Error fetching time"
    }
}