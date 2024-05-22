import React, { useEffect, useState } from "react";
import { Route, Routes } from "react-router-dom";
import Home from "../pages/Home/Home";
import Genres from "../pages/Genres/Genres";
import MovieView from "../pages/MovieView/MovieView";
import Sidebar from "../components/sidebar/Sidebar";

export default function Router() {
  const shouldBeOpen = () => window.innerWidth > 800;
  const [showSidebar, setShowSidebar] = useState(shouldBeOpen);

  const contentStyle = {
    padding: "10px 0",
    marginLeft: showSidebar ? "260px" : "90px",
    transition: "margin 0.2s ease",
    borderRadius: "30px",
  };

  // Resize Sidebar
  useEffect(() => {
    const handleResize = () => {
      setShowSidebar(shouldBeOpen());
    };
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  return (
    <div>
      <Sidebar showSidebar={showSidebar} setShowSidebar={setShowSidebar} />
      <main style={contentStyle}>
        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/movies/genres/:genre" element={<Genres />}></Route>
          <Route path="/movies/:imdbId" element={<MovieView />}></Route>
        </Routes>
      </main>
    </div>
  );
}
