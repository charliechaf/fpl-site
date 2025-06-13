import React, { useEffect, useState } from 'react';

const tableStyles = {
  width: '100%',
  borderCollapse: 'separate',
  borderSpacing: 0,
  background: '#fff',
  boxShadow: '0 2px 8px rgba(0,0,0,0.06)',
  borderRadius: '12px',
  overflow: 'hidden',
  fontFamily: 'system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif',
};

const thStyles = {
  background: '#fafafb',
  color: '#222',
  fontWeight: 600,
  padding: '14px 16px',
  borderBottom: '1px solid #e9ecef',
  textAlign: 'left',
  fontSize: '15px',
};

const tdStyles = {
  padding: '14px 16px',
  borderBottom: '1px solid #f1f3f4',
  fontSize: '15px',
  color: '#444',
  background: '#fff',
};

const trHover = {
  transition: 'background 0.2s',
};

const TableView = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [event, setEvent] = useState(null);
  const [chicagoTime, setChicagoTime] = useState('');

  useEffect(() => {
    fetch('/fpl-site/data.json')
      .then((res) => res.json())
      .then((json) => {
        setData(json);
        if (json.length > 0 && json[0].entry_history) {
          setEvent(json[0].entry_history.event);
        }
        setLoading(false);
      });
  }, []);

  useEffect(() => {
    fetch('http://worldtimeapi.org/api/timezone/America/Chicago')
      .then((res) => res.json())
      .then((json) => {
        setChicagoTime(json.datetime ? new Date(json.datetime).toLocaleString('en-US', { timeZone: 'America/Chicago' }) : '');
      })
      .catch(() => setChicagoTime('Error fetching time'));
  }, []);

  if (loading) return <div>Loading...</div>;

  // Sort data by net points descending
  const sortedData = [...data].sort((a, b) => {
    const netA = a.entry_history ? a.entry_history.points - a.entry_history.event_transfers_cost : -Infinity;
    const netB = b.entry_history ? b.entry_history.points - b.entry_history.event_transfers_cost : -Infinity;
    return netB - netA;
  });

  return (
    <div style={{ maxWidth: 700, margin: '40px auto', padding: 24, boxSizing: 'border-box', width: '100%', overflowX: 'auto' }}>
      <h2 style={{ fontWeight: 700, fontSize: 24, marginBottom: 24 }}>Gameweek: {event}</h2>
      <div style={{ marginBottom: 16, fontSize: 16, color: '#555' }}>Chicago Time: {chicagoTime}</div>
      <div style={{ width: '100%', overflowX: 'auto' }}>
        <table style={tableStyles}>
          <thead>
            <tr>
              <th style={thStyles}>name</th>
              <th style={thStyles}>points</th>
              <th style={thStyles}>transfer cost</th>
              <th style={thStyles}>net points</th>
              <th style={thStyles}>active chip</th>
            </tr>
          </thead>
          <tbody>
            {sortedData.map((row, idx) => (
              <tr key={idx} style={trHover} onMouseOver={e => e.currentTarget.style.background='#f7f7f9'} onMouseOut={e => e.currentTarget.style.background='#fff'}>
                <td style={tdStyles}>{row.name || ''}</td>
                <td style={tdStyles}>{row.entry_history?.points}</td>
                <td style={tdStyles}>{row.entry_history?.event_transfers_cost}</td>
                <td style={tdStyles}>{row.entry_history ? row.entry_history.points - row.entry_history.event_transfers_cost : ''}</td>
                <td style={tdStyles}>{row.active_chip || ''}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default TableView;
