import React from "react";
import "./Card.css";
import { Link } from "react-router-dom";

export default function Card({ poster, title, id }) {
  return (
    <div className="Card">
      <Link to={`/movies/${id}`} className="link">
        <div>
          <img src={poster} alt="Movie Poster"></img>
          <hr />
        </div>
        <div className="title-container">
          <p>{title}</p>
        </div>
      </Link>
    </div>
  );
}
