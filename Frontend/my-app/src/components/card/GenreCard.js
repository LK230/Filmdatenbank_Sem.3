import React from "react";
import "./GenreCard.css";
import { Link } from "react-router-dom";

export default function GenreCard({ title, id }) {
  return (
    <div className="GenreCard">
      <Link to={`/movie:${id}`} className="link">
        <div className="title-container">
          <p>{title}</p>
        </div>
      </Link>
    </div>
  );
}
