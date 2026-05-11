import { BrowserRouter, Routes, Route } from "react-router-dom";
import ListPage from "./pages/ListPage";  // ← ADD THIS

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<ListPage />} />
        <Route path="/list" element={<ListPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;