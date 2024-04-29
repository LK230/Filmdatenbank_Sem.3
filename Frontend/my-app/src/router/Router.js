import React from "react";
import { Route, Routes } from "react-router-dom";
import Home from "../pages/Home/Home";
import Genres from "../pages/Genres/Genres";

export default function Router() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<Home />}></Route>
        <Route path="/genres" element={<Genres />}></Route>
      </Routes>
    </div>
  );
}
