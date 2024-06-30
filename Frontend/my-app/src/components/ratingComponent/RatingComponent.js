import React, { useEffect, useState } from "react";
import RatingStarUnclicked from "../../assets/images/icons/RatingStarUnclicked.svg";
import RatingStarClicked from "../../assets/images/icons/RatingStarClicked.svg";
import ButtonComponent from "../../components/buttonComponent/ButtonComponent";
import "./RatingComponent.css";
import { ReviewService } from "../../assets/service/review_service";

export default function RatingComponent({ user, imdbId }) {
  const [rating, setRating] = useState(0);
  const [hoverRating, setHoverRating] = useState(0);
  const [inputCount, setInputCount] = useState("");

  function handleClick(value) {
    setRating(value);
  }

  function handleMouseEnter(value) {
    setHoverRating(value);
  }

  function handleMouseLeave(value) {
    setHoverRating(0);
  }

  const handleInputChange = (e) => {
    setInputCount(e.target.value);
  };

  const handleCreateReview = async () => {
    try {
      const createReview = await new ReviewService().getCreateReview(
        inputCount,
        rating,
        imdbId,
        user
      );
      console.log("createReview", createReview);
    } catch (error) {
      console.error("Error deleting from favorites:", error);
      throw error;
    }
  };

  return (
    <div className="RatingContainer">
      <h2>Bewertung</h2>
      <div>
        {[1, 2, 3, 4, 5].map((value) => {
          let imageSrc;
          if ((hoverRating || rating) >= value) {
            imageSrc = RatingStarClicked;
          } else {
            imageSrc = RatingStarUnclicked;
          }
          return (
            <button
              onClick={() => handleClick(value)}
              onMouseEnter={() => handleMouseEnter(value)}
              onMouseLeave={handleMouseLeave}>
              <img src={imageSrc} alt="Rating Star" />
            </button>
          );
        })}
      </div>

      <div className="InputContainer">
        <div className="CommentContainer">
          <textarea
            placeholder="Kommentar"
            value={inputCount}
            onChange={handleInputChange}
            rows="5"
            maxLength="60"
          />
        </div>
        <div className="CharacterCountContainer">{inputCount.length}/60</div>
        <div>
          <ButtonComponent
            label="Abschicken"
            onClick={handleCreateReview}></ButtonComponent>
        </div>
      </div>
    </div>
  );
}
