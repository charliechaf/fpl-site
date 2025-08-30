import kotlin.js.Promise

external val window: dynamic
external val document: dynamic

external interface Response {
    val ok: Boolean
    fun text(): Promise<String>
}

external fun fetch(url: String): Promise<Response>