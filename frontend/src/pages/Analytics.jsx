import { useEffect, useState } from "react";
import axios from "axios";
import { BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid } from "recharts";

export default function Analytics() {
  const [stats, setStats] = useState(null);

  useEffect(() => {
    axios.get("http://localhost:8080/api/stats")
      .then(res => setStats(res.data));
  }, []);

  if (!stats) return <div className="p-6">Loading analytics...</div>;

  const data = [
    { name: "Total", value: stats.total },
    { name: "Low (<50)", value: stats.low },
    { name: "Good (>=80)", value: stats.good },
    { name: "Average", value: stats.avg }
  ];

  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-6">Analytics Dashboard</h1>

      <BarChart width={600} height={300} data={data}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis />
        <Tooltip />
        <Bar dataKey="value" />
      </BarChart>
    </div>
  );
}