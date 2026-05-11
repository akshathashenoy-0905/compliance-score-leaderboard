import { BrowserRouter, Routes, Route } from "react-router-dom";
import Analytics from "./pages/Analytics";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/analytics" element={<Analytics />} />
      </Routes>
    </BrowserRouter>
  );
}