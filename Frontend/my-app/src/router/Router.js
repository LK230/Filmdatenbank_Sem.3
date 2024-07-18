import React, { useEffect, useState } from "react";
import { Route, Routes, useLocation } from "react-router-dom";
import Home from "../pages/Home/Home";
import Genres from "../pages/Genres/Genres";
import MovieView from "../pages/MovieView/MovieView";
import Sidebar from "../components/sidebar/Sidebar";
import Login from "../pages/Login/LoginSignup";
import ProfileSettings from "../pages/ProfileSettings/ProfileSettings";
import FavoritePage from "../pages/FavoritePage/FavoritePage";
import { GenreView } from "../pages/Genres/GenreView";
import Header from "../components/header/Header";

export default function Router() {
  const shouldBeOpen = () => window.innerWidth > 1100;
  const [showSmallSidebar, setShowSmallSidebar] = useState(shouldBeOpen);
  const [showSidebar, setShowSidebar] = useState(window.innerWidth > 900);
  const [showHeader, setShowHeader] = useState(window.innerWidth <= 900);
  const path = useLocation();

  const contentStyle = {
    marginLeft: showHeader ? "0" : showSmallSidebar ? "260px" : "90px",
    transition: "margin 0.2s ease",
    borderRadius: "30px",
  };

  useEffect(() => {
    const handleResize = () => {
      setShowSmallSidebar(shouldBeOpen());
      setShowHeader(window.innerWidth <= 900);
      if (window.innerWidth <= 900) {
        setShowSidebar(false);
        console.log("showSidebar", showSidebar);
      } else {
        setShowSidebar(true);
      }
    };
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  const isLoginPage = path.pathname === "/login";

  return (
    <div>
      {!isLoginPage && showSidebar && (
        <Sidebar
          showSidebar={showSmallSidebar}
          setShowSidebar={setShowSmallSidebar}
        />
      )}
      {!isLoginPage && showHeader && !showSidebar && <Header />}

      <main style={isLoginPage ? {} : contentStyle}>
        <Routes>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/" element={<Home />}></Route>
          <Route path="/genres" element={<Genres />}></Route>
          <Route
            path="/movies/genreview/:genre"
            element={<GenreView />}></Route>
          <Route path="/movies/:imdbId" element={<MovieView />}></Route>
          <Route path="/profile" element={<ProfileSettings />}></Route>
          <Route path="/favorites" element={<FavoritePage />}></Route>
        </Routes>
      </main>
    </div>
  );
}
