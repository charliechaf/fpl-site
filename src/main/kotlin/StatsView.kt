import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2

external interface StatsViewProps : Props

val StatsView = FC<StatsViewProps> { _ ->
    div {
        className = "statsview-placeholder".asDynamic()
        
        h2 {
            +"Stats View (Coming Soon)"
        }
    }
}
