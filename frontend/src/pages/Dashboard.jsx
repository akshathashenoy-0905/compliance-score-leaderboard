import { useEffect, useState } from "react";
import { getStats } from "../services/statsService";

export default function Dashboard() {
  const [stats, setStats] = useState(null);

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    try {
      const res = await getStats();
      console.log("STATS RESPONSE:", res);
      setStats(res);
    } catch (err) {
      console.log("Error loading stats", err);
    }
  };

  if (!stats) return <div>Loading Dashboard...</div>;

  return (
    <div style={{ padding: "20px" }}>
      <h2>Dashboard</h2>

      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(4, 1fr)",
          gap: "15px",
          marginTop: "20px",
        }}
      >
        <div style={cardStyle}>
          <h3>Total Records</h3>
          <p style={valueStyle}>{stats.total}</p>
        </div>

        <div style={cardStyle}>
          <h3>Low Compliance</h3>
          <p style={valueStyle}>{stats.low}</p>
        </div>

        <div style={cardStyle}>
          <h3>Good Compliance</h3>
          <p style={valueStyle}>{stats.good}</p>
        </div>

        <div style={cardStyle}>
          <h3>Average Score</h3>
          <p style={valueStyle}>{stats.avg}</p>
        </div>
      </div>
    </div>
  );
}

const cardStyle = {
  padding: "25px",
  borderRadius: "12px",
  background: "#fff",
  textAlign: "center",
  boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
  border: "1px solid #eee",
};

const valueStyle = {
  fontSize: "26px",
  fontWeight: "bold",
  marginTop: "10px",
};