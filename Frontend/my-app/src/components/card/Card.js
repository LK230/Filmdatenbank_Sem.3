import React from "react";
import "./Card.css";
import { Link } from "react-router-dom";
import moviePoster from "../../assets/images/Avatar-The-Way-Of-Water.jpg";

export default function Card() {
  return (
    <div className="Card">
      <Link to="/movie:id" className="link">
        <div>
          <img src={moviePoster} alt="Movie Poster"></img>
        </div>
        <div>
          <div className="line"></div>
        </div>
        <div>
          <p>Avatar</p>
        </div>
      </Link>
    </div>
  );
}
