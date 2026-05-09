import { useState, useMemo } from "react";
import _ from "lodash";

export default function SearchBar({ onSearch }) {
  const [q, setQ] = useState("");
  const [status, setStatus] = useState("");
  const [from, setFrom] = useState("");
  const [to, setTo] = useState("");

  const debouncedSearch = useMemo(
    () =>
      _.debounce((params) => {
        onSearch(params);
      }, 500),
    [onSearch]
  );

  const handleChange = () => {
    debouncedSearch({ q, status, from, to });
  };

  return (
    <div className="flex gap-2 mb-4">
      <input
        placeholder="Search..."
        className="border p-2"
        onChange={(e) => {
          setQ(e.target.value);
          handleChange();
        }}
      />

      <select
        className="border p-2"
        onChange={(e) => {
          setStatus(e.target.value);
          handleChange();
        }}
      >
        <option value="">All Status</option>
        <option value="COMPLIANT">Compliant</option>
        <option value="NON_COMPLIANT">Non Compliant</option>
      </select>

      <input type="date" className="border p-2"
        onChange={(e) => {
          setFrom(e.target.value);
          handleChange();
        }}
      />

      <input type="date" className="border p-2"
        onChange={(e) => {
          setTo(e.target.value);
          handleChange();
        }}
      />
    </div>
  );
}