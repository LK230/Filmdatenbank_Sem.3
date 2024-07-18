import React from "react";
import RatingStars from "./RatingStars";
import "./RatingView.css";

export default function RatingView({ user, comment, rating }) {
  return (
    <div className="RatingView">
      <div className="top-container">
        <div className="user-container">
          <p>{user}</p>
        </div>
        <div className="rating-container">
          <div>
            <RatingStars rating={rating} />
          </div>
        </div>
      </div>
      <div className="comment-container">
        <p>{comment}</p>
      </div>
    </div>
  );
}
