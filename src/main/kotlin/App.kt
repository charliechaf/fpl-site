import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.img

external interface AppProps : Props

val App = FC<AppProps> { _ ->
    var tab by useState("table")

    div {
        className = "app-container".asDynamic()
        
        div {
            style = js("{minHeight: 'calc(100vh - 56px)'}")
            
            when (tab) {
                "table" -> TableView()
                else -> StatsView()
            }
        }
        
        div {
            className = "bottom-tabs".asDynamic()
            
            button {
                className = (if (tab == "table") "tab-btn active" else "tab-btn").asDynamic()
                onClick = { _ ->
                    tab = "table"
                }
                
                img {
                    src = "./table-icon.svg"
                    alt = "Table"
                    className = "tab-icon".asDynamic()
                }
            }
            
            button {
                className = (if (tab == "stats") "tab-btn active" else "tab-btn").asDynamic()
                onClick = { _ ->
                    tab = "stats"
                }
                +"Stats"
            }
        }
    }
}