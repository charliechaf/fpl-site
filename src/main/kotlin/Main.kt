import react.*
import react.dom.client.createRoot

fun main() {
    val container = document.getElementById("root")!!
    createRoot(container).render(
        StrictMode.create {
            App()
        }
    )
}