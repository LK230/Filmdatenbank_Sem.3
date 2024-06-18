import React from "react";
import "./GenreCard.css";
import { Link } from "react-router-dom";

export default function GenreCard({ genre }) {
  console.log("genreCard", genre);
  return (
    <div className="GenreCard">
      <Link to={`/movie/genre/${genre}`} className="link">
        <div className="title-container">
          <p>{genre}</p>
        </div>
      </Link>
    </div>
  );
}
