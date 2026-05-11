import { useEffect, useState } from "react";
import axios from "axios";

export default function ListPage() {
  const [data, setData] = useState([]);
  const [search, setSearch] = useState("");
  const [filter, setFilter] = useState("ALL");

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/all")
      .then((res) => setData(res.data))
      .catch((err) => console.log(err));
  }, []);

  const filteredData = data
    .filter((item) =>
      item.employeeName.toLowerCase().includes(search.toLowerCase())
    )
    .filter((item) => {
      if (filter === "ALL") return true;
      return item.status === filter;
    });

  return (
    <div style={styles.container}>
      <h2 style={styles.title}>Compliance List</h2>

      {/* SEARCH + FILTER */}
      <div style={styles.topBar}>
        <input
          style={styles.input}
          placeholder="Search employee..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />

        <select
          style={styles.select}
          value={filter}
          onChange={(e) => setFilter(e.target.value)}
        >
          <option value="ALL">All Status</option>
          <option value="GOOD">GOOD</option>
          <option value="BAD">BAD</option>
        </select>
      </div>

      {/* RESPONSIVE TABLE */}
      <div style={styles.tableWrapper}>
        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>ID</th>
              <th style={styles.th}>Employee Name</th>
              <th style={styles.th}>Department</th>
              <th style={styles.th}>Score</th>
              <th style={styles.th}>Status</th>
            </tr>
          </thead>

          <tbody>
            {filteredData.map((item) => (
              <tr key={item.id}>
                <td style={styles.td}>{item.id}</td>
                <td style={styles.td}>{item.employeeName}</td>
                <td style={styles.td}>{item.department}</td>
                <td style={styles.td}>{item.score}</td>
                <td
                  style={{
                    ...styles.td,
                    fontWeight: "bold",
                    color: item.status === "GOOD" ? "green" : "red",
                  }}
                >
                  {item.status}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

/* INLINE CSS (SAFE + NO TAILWIND ISSUES) */
const styles = {
  container: {
    padding: "20px",
    fontFamily: "Arial",
  },
  title: {
    textAlign: "center",
    marginBottom: "20px",
  },
  topBar: {
    display: "flex",
    justifyContent: "space-between",
    marginBottom: "15px",
    flexWrap: "wrap",
    gap: "10px",
  },
  input: {
    padding: "8px",
    width: "200px",
  },
  select: {
    padding: "8px",
  },
  tableWrapper: {
    overflowX: "auto",
  },
  table: {
    width: "100%",
    borderCollapse: "collapse",
    minWidth: "600px",
  },
  th: {
    border: "1px solid #ccc",
    padding: "10px",
    backgroundColor: "#f4f4f4",
  },
  td: {
    border: "1px solid #ccc",
    padding: "10px",
    textAlign: "center",
  },
};