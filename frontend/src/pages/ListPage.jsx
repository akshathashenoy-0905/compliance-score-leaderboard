import { useEffect, useState } from "react";
import api from "../services/api";
import SearchBar from "../components/SearchBar";
import { searchScores } from "../services/api";
export default function ListPage() {
  const [data, setData] = useState([]);
const handleSearch = async (params) => {
  const res = await searchScores(params);
  setScores(res.data);
};
  useEffect(() => {
    api
      .get("/all?page=0&size=10")
      .then((res) => {
        setData(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <div className="p-6">
      <h2 className="text-2xl mb-4">Compliance Records</h2>
<SearchBar onSearch={handleSearch} />
      <table className="table-auto border w-full">
        <thead>
          <tr className="bg-gray-200">
            <th className="border p-2">ID</th>
            <th className="border p-2">Name</th>
            <th className="border p-2">Score</th>
            <th className="border p-2">Status</th>
          </tr>
        </thead>

        <tbody>
          {data.map((item) => (
            <tr key={item.id}>
              <td className="border p-2">{item.id}</td>
              <td className="border p-2">{item.name}</td>
              <td className="border p-2">{item.score}</td>
              <td className="border p-2">{item.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}