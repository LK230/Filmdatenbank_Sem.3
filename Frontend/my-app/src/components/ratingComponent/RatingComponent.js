import React, { useState } from "react";
import RatingStarUnclicked from "../../assets/images/icons/RatingStarUnclicked.svg";
import RatingStarClicked from "../../assets/images/icons/RatingStarClicked.svg";
import ButtonComponent from "../../components/buttonComponent/ButtonComponent";
import "./RatingComponent.css";
import { ReviewService } from "../../assets/service/review_service";
import Alert from "../alert/Alert.js";

export default function RatingComponent({ user, imdbId }) {
  const [rating, setRating] = useState(0);
  const [hoverRating, setHoverRating] = useState(0);
  const [inputCount, setInputCount] = useState("");
  const [alertMessage, setAlertMessage] = useState("");
  const [alertType, setAlertType] = useState("");

  function handleClick(value) {
    setRating(value);
  }

  function handleMouseEnter(value) {
    setHoverRating(value);
  }

  function handleMouseLeave() {
    setHoverRating(0);
  }

  const handleInputChange = (e) => {
    setInputCount(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await new ReviewService().getCreateReview(
        inputCount,
        rating,
        imdbId,
        user
      );
      setAlertMessage("Review submitted successfully!");
      setAlertType("success");
      setTimeout(() => {
        window.location.reload();
      }, 2000);
    } catch (error) {
      setAlertMessage("Error submitting review. Please try again.");
      setAlertType("error");
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
              key={value}
              onClick={() => handleClick(value)}
              onMouseEnter={() => handleMouseEnter(value)}
              onMouseLeave={handleMouseLeave}
              className="rating-star-button">
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
            onClick={handleSubmit}></ButtonComponent>
        </div>
      </div>
      <Alert message={alertMessage} type={alertType} />
    </div>
  );
}
