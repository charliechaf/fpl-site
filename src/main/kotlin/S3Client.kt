import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class EntryHistory(
    val points: Int? = null,
    val event_transfers_cost: Int? = null,
    val event: Int? = null
)

@Serializable
data class PlayerData(
    val name: String? = null,
    val entry_history: EntryHistory? = null,
    val active_chip: String? = null
)

class S3Client(private val url: String) {
    
    suspend fun getData(): List<PlayerData> {
        return try {
            val response = fetch(url).await()
            if (!response.ok) throw Exception("Failed to fetch S3 data")
            val jsonText = response.text().await()
            Json { ignoreUnknownKeys = true }.decodeFromString<List<PlayerData>>(jsonText)
        } catch (e: Exception) {
            emptyList()
        }
    }
}

val s3Client = S3Client("https://fpl-site-2025.s3.us-east-1.amazonaws.com/fake-fpl-data.json")