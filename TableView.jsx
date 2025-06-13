import React, { useEffect, useState } from 'react';
import { fetchChicagoTime } from './fetchChicagoTime';
import { s3Client } from './s3Client';
import './TableView.css';

const TableView = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [event, setEvent] = useState(null);
  const [chicagoTime, setChicagoTime] = useState('');

  useEffect(() => {
    s3Client.getData().then((json) => {
      setData(json);
      if (json.length > 0 && json[0].entry_history) {
        setEvent(json[0].entry_history.event);
      }
      setLoading(false);
    });
  }, []);

  useEffect(() => {
    fetchChicagoTime().then(setChicagoTime);
  }, []);

  if (loading) return <div>Loading...</div>;

  // Sort data by net points descending
  const sortedData = [...data].sort((a, b) => {
    const netA = a.entry_history ? a.entry_history.points - a.entry_history.event_transfers_cost : -Infinity;
    const netB = b.entry_history ? b.entry_history.points - b.entry_history.event_transfers_cost : -Infinity;
    return netB - netA;
  });

  return (
    <div className="tableview-outer">
      <div className="tableview-inner">
        <h2 className="tableview-title">Gameweek: {event}</h2>
        <div className="tableview-time">Chicago Time: {chicagoTime}</div>
        <div style={{ width: '100%', overflowX: 'auto', boxSizing: 'border-box' }}>
          <table className="tableview-table">
            <thead>
              <tr className="tableview-tr">
                <th className="tableview-th">name</th>
                <th className="tableview-th">points</th>
                <th className="tableview-th">transfer cost</th>
                <th className="tableview-th">net points</th>
                <th className="tableview-th">active chip</th>
              </tr>
            </thead>
            <tbody>
              {sortedData.map((row, idx) => (
                <tr className="tableview-tr" key={idx}>
                  <td className="tableview-td">{row.name || ''}</td>
                  <td className="tableview-td">{row.entry_history?.points}</td>
                  <td className="tableview-td">{row.entry_history?.event_transfers_cost}</td>
                  <td className="tableview-td">{row.entry_history ? row.entry_history.points - row.entry_history.event_transfers_cost : ''}</td>
                  <td className="tableview-td">{row.active_chip || ''}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default TableView;
