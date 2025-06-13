import { useState } from 'react'
import TableView from './TableView';
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <TableView />
    </>
  )
}

export default App
