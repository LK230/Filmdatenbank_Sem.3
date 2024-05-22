import React from "react";
import { Route, Routes } from "react-router-dom";
import Home from "../pages/Home/Home";
import Genres from "../pages/Genres/Genres";
import MovieView from "../pages/MovieView/MovieView";

export default function Router() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<Home />}></Route>
        <Route path="/movies/genres/:genre" element={<Genres />}></Route>
        <Route path="/movies/:imdbId" element={<MovieView />}></Route>
      </Routes>
    </div>
  );
}
