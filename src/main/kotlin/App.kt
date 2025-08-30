import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.main

external interface AppProps : Props

val App = FC<AppProps> { _ ->
    div {
        className = "app".asDynamic()
        
        header {
            className = "app-header".asDynamic()
            h1 {
                className = "app-title".asDynamic()
                +"FPL Leaderboard"
            }
        }
        
        main {
            className = "app-main".asDynamic()
            TableView()
        }
    }
}