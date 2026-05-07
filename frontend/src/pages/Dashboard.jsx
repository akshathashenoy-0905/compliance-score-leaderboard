import { useEffect, useState } from "react";

function Dashboard() {
  const [stats, setStats] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/stats")
      .then((res) => res.json())
      .then((data) => {
        console.log("Stats received:", data);
        setStats(data);
      })
      .catch((err) => {
        console.error("Error fetching stats:", err);
      });
  }, []);

  if (!stats) return <h2>Loading stats...</h2>;

  return (
    <div style={{ padding: "20px" }}>
      <h1>Compliance Stats Dashboard</h1>
      <p>Total Records: {stats.total}</p>
      <p>Average Score: {stats.avgScore}</p>
      <p>Low Compliance: {stats.low}</p>
      <p>Good Compliance: {stats.good}</p>
    </div>
  );
}

export default Dashboard;