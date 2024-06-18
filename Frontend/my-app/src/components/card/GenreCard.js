import React from "react";
import "./GenreCard.css";
import { Link } from "react-router-dom";

export default function GenreCard({genre, title}) {
  return (
    <div className="GenreCard">
      <Link to={`/movie/genre` + genre} className="link">
        <div className="title-container">
          <p>{title}</p>
        </div>
      </Link>
    </div>
  );
}
