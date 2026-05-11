import { useEffect, useState } from "react";
import axios from "axios";

export default function ListPage() {
  const [data, setData] = useState([]);
  const [search, setSearch] = useState("");
  const [loading, setLoading] = useState(true);
const [status, setStatus] = useState("");
  useEffect(() => {
    fetchData();
  }, []);
useEffect(() => {
  fetchData();
}, [status]);
  useEffect(() => {
    const delay = setTimeout(() => {
      fetchData();
    }, 400); // debounce

    return () => clearTimeout(delay);
  }, [search]);

  const fetchData = async () => {
    try {
      setLoading(true);

      let url = "http://localhost:8080/api/all";

      if (search.trim() !== "") {
        url = `http://localhost:8080/api/search?q=${search}`;
      }

      const res = await axios.get(url);

      let filtered = res.data;

      if (status !== "") {
        filtered = filtered.filter((item) => item.status === status);
      }

      setData(filtered);
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div style={{ padding: "20px" }}>
      <h2>Compliance List</h2>

      {/* SEARCH BOX */}
      <input
        type="text"
        placeholder="Search employee..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        style={{
          padding: "10px",
          width: "300px",
          marginTop: "10px",
        }}
      />
<select
  value={status}
  onChange={(e) => setStatus(e.target.value)}
  style={{ padding: "10px", marginLeft: "10px" }}
>
  <option value="">All Status</option>
  <option value="GOOD">GOOD</option>
  <option value="LOW">LOW</option>
  <option value="AVERAGE">AVERAGE</option>
</select>
      {/* TABLE */}
      {data.length === 0 ? (
        <p>No records found</p>
      ) : (
        <table border="1" cellPadding="10" style={{ marginTop: "20px", width: "100%" }}>
          <thead>
            <tr>
              <th>ID</th>
              <th>Employee Name</th>
              <th>Department</th>
              <th>Score</th>
              <th>Status</th>
            </tr>
          </thead>

          <tbody>
            {data.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.employeeName}</td>
                <td>{item.department}</td>
                <td>{item.score}</td>
                <td>{item.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}