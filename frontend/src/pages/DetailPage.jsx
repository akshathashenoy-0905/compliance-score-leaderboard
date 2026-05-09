import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../services/api";

export default function DetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [record, setRecord] = useState(null);

  useEffect(() => {
    api.get(`/${id}`)
      .then(res => setRecord(res.data))
      .catch(err => console.log(err));
  }, [id]);

  const handleDelete = () => {
    api.delete(`/${id}`)
      .then(() => {
        alert("Deleted successfully");
        navigate("/");
      })
      .catch(err => console.log(err));
  };

  if (!record) return <p>Loading...</p>;

  const scoreBadge =
    record.score < 50 ? "Low" :
    record.score < 80 ? "Medium" : "High";

  return (
    <div style={{ padding: "20px" }}>
      <h2>Record Detail</h2>

      <p><b>ID:</b> {record.id}</p>
      <p><b>Name:</b> {record.name}</p>
      <p><b>Score:</b> {record.score}</p>
      <p><b>Status:</b> {record.status}</p>

      <h3>Score Badge: {scoreBadge}</h3>

      <button onClick={() => navigate(`/edit/${id}`)}>Edit</button>
      <button onClick={handleDelete} style={{ marginLeft: "10px" }}>
        Delete
      </button>
    </div>
  );
}