import React from "react";
import "./GenreCard.css";
import { Link } from "react-router-dom";

export default function GenreCard({ genre }) {
  return (
   
      <Link to={`/movies/genreview/${genre}`} className="link">
         <div className="GenreCard">
        <div className="title-container">
          <p>{genre}</p>
        </div>
        </div>
      </Link>
    
  );
}
