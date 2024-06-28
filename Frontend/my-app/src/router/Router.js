import React, { useEffect, useState } from "react";
import { Route, Routes, useLocation } from "react-router-dom";
import Home from "../pages/Home/Home";
import Genres from "../pages/Genres/Genres";
import MovieView from "../pages/MovieView/MovieView";
import Sidebar from "../components/sidebar/Sidebar";
import Login from "../pages/Login/LoginSignup";
import { GenreView } from "../pages/Genres/GenreView";

export default function Router() {
  const shouldBeOpen = () => window.innerWidth > 1200;
  const [showSidebar, setShowSidebar] = useState(shouldBeOpen);
  const path = useLocation();

  const contentStyle = {
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

  const isLoginPage = path.pathname === "/login";

  return (
    <div>
      {!isLoginPage && (
        <Sidebar showSidebar={showSidebar} setShowSidebar={setShowSidebar} />
      )}

      <main style={isLoginPage ? {} : contentStyle}>
        <Routes>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/" element={<Home />}></Route>
          <Route path="/genres" element={<Genres/>}></Route>
          <Route path="/movies/genreview/:genre" element={<GenreView />}></Route>
          <Route path="/movies/:imdbId" element={<MovieView />}></Route>
        </Routes>
      </main>
    </div>
  );
}
