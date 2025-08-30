import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.thead
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.tr
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.img
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

external interface TableViewProps : Props

val TableView = FC<TableViewProps> { _ ->
    var data by useState<List<PlayerData>>(emptyList())
    var loading by useState(true)
    var event by useState<Int?>(null)
    var chicagoTime by useState("")

    useEffect {
        val scope = MainScope()
        
        scope.launch {
            val jsonData = s3Client.getData()
            data = jsonData
            if (jsonData.isNotEmpty() && jsonData[0].entry_history != null) {
                event = jsonData[0].entry_history!!.event
            }
            loading = false
        }
        
        scope.launch {
            chicagoTime = fetchChicagoTime()
        }
    }

    if (loading) {
        div {
            +"Loading..."
        }
        return@FC
    }

    // Sort data by net points descending
    val sortedData = data.sortedByDescending { playerData ->
        playerData.entry_history?.let { history ->
            (history.points ?: 0) - (history.event_transfers_cost ?: 0)
        } ?: Int.MIN_VALUE
    }

    div {
        className = "tableview-outer".asDynamic()
        
        div {
            className = "tableview-inner".asDynamic()
            
            h2 {
                className = "tableview-title".asDynamic()
                +"Gameweek: ${event ?: ""}"
            }
            
            div {
                className = "tableview-time".asDynamic()
                +"Chicago Time: $chicagoTime"
            }
            
            div {
                style = js("{width: '100%', overflowX: 'auto', boxSizing: 'border-box'}")
                
                table {
                    className = "tableview-table".asDynamic()
                    
                    thead {
                        tr {
                            className = "tableview-tr".asDynamic()
                            
                            th {
                                className = "tableview-th".asDynamic()
                                +"name"
                            }
                            th {
                                className = "tableview-th".asDynamic()
                                +"points"
                            }
                            th {
                                className = "tableview-th".asDynamic()
                                +"transfer cost"
                            }
                            th {
                                className = "tableview-th".asDynamic()
                                +"net points"
                            }
                            th {
                                className = "tableview-th".asDynamic()
                                +"active chip"
                            }
                        }
                    }
                    
                    tbody {
                        sortedData.forEachIndexed { idx, row ->
                            tr {
                                className = "tableview-tr".asDynamic()
                                key = idx.toString()
                                
                                td {
                                    className = "tableview-td".asDynamic()
                                    +(row.name ?: "")
                                }
                                
                                td {
                                    className = "tableview-td".asDynamic()
                                    +(row.entry_history?.points?.toString() ?: "")
                                }
                                
                                td {
                                    className = "tableview-td".asDynamic()
                                    +(row.entry_history?.event_transfers_cost?.toString() ?: "")
                                }
                                
                                td {
                                    className = "tableview-td".asDynamic()
                                    val netPoints = row.entry_history?.let { history ->
                                        (history.points ?: 0) - (history.event_transfers_cost ?: 0)
                                    }
                                    +(netPoints?.toString() ?: "")
                                }
                                
                                td {
                                    className = "tableview-td".asDynamic()
                                    if (row.active_chip == "triple_captain") {
                                        img {
                                            src = "./triple-captain-icon.svg"
                                            alt = "Triple Captain"
                                            style = js("{height: '32px', width: '32px', display: 'inline-block', verticalAlign: 'middle'}")
                                        }
                                    } else {
                                        +(row.active_chip ?: "")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}