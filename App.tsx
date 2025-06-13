import React, { useState } from 'react';
import TableView from './TableView';
import StatsView from './StatsView';
import './App.css';
import tableIcon2 from './table-icon-2.png';

function App() {
  const [tab, setTab] = useState('table');

  return (
    <div className="app-container">
      <div style={{ minHeight: 'calc(100vh - 56px)' }}>
        {tab === 'table' ? <TableView /> : <StatsView />}
      </div>
      <div className="bottom-tabs">
        <button
          className={tab === 'table' ? 'tab-btn active' : 'tab-btn'}
          onClick={() => setTab('table')}
        >
          <img src={tableIcon2} alt="Table" style={{ height: 28, verticalAlign: 'middle' }} />
        </button>
        <button
          className={tab === 'stats' ? 'tab-btn active' : 'tab-btn'}
          onClick={() => setTab('stats')}
        >
          Stats
        </button>
      </div>
    </div>
  );
}

export default App;
