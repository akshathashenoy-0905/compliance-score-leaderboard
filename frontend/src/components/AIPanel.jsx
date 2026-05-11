import { useState } from "react";

export default function AIPanel() {
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState("");

  const askAI = () => {
    setLoading(true);
    setResult("");

    setTimeout(() => {
      setResult("AI Insight: Overall compliance trend is improving. Focus on low-score entities.");
      setLoading(false);
    }, 1200);
  };

  return (
    <div className="p-4 border rounded-xl shadow mt-4">
      <h2 className="text-xl font-semibold mb-2">AI Insights</h2>

      <button
        onClick={askAI}
        className="bg-blue-600 text-white px-4 py-2 rounded"
      >
        Get Insight
      </button>

      {loading && <p className="mt-3">Loading...</p>}

      {result && (
        <div className="mt-3 p-3 bg-gray-100 rounded">
          {result}
        </div>
      )}
    </div>
  );
}