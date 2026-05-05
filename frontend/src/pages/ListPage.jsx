import { useEffect, useState } from "react";
import API from "../services/api";

export default function ListPage() {
  const [data, setData] = useState([]);

  const fetchData = () => {
    API.get("/api/compliance/all")
      .then(res => setData(res.data))
      .catch(err => console.log(err));
  };

  const handleDelete = (id) => {
    API.delete(`/api/compliance/${id}`)
      .then(() => fetchData())
      .catch(err => console.log(err));
  };

  const handleEdit = (item) => {
    const newName = prompt("Enter new name", item.employeeName);
    if (!newName) return;

    API.put(`/api/compliance/update/${item.id}`, {
      ...item,
      employeeName: newName
    }).then(() => fetchData());
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div style={{ padding: "20px" }}>
      <h2>Compliance Score Leaderboard</h2>

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th>
            <th>Employee</th>
            <th>Score</th>
            <th>Department</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {data.map(item => (
            <tr key={item.id}>
              <td>{item.id}</td>

              <td
                onClick={() => handleEdit(item)}
                style={{ cursor: "pointer", color: "blue" }}
              >
                {item.employeeName}
              </td>

              <td>{item.score}</td>
              <td>{item.department}</td>
              <td>{item.status}</td>

              <td>
                <button onClick={() => handleDelete(item.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}