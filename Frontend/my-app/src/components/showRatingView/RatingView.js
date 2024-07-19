import React from "react";
import RatingStars from "./RatingStars";
import { FaRegTrashAlt } from "react-icons/fa";
import "./RatingView.css";

export default function RatingView({
  user,
  comment,
  rating,
  onClick,
  loggedIn,
}) {
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
      {loggedIn && (
        <div className="trash-container">
          <button onClick={onClick}>
            <FaRegTrashAlt />
          </button>
        </div>
      )}
    </div>
  );
}
