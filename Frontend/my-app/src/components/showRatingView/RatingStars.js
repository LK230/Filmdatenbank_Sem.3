import React from "react";
import RatingStarUnclicked from "../../assets/images/icons/RatingStarUnclicked.svg";
import RatingStarClicked from "../../assets/images/icons/RatingStarClicked.svg";

const RatingStars = ({ rating }) => {
  const totalStars = 5;
  let stars = [];

  for (let i = 0; i < totalStars; i++) {
    if (i < rating) {
      stars.push(
        <img
          key={i}
          src={RatingStarClicked}
          alt="clicked star"
          style={{ width: "35px" }}
        />
      );
    } else {
      stars.push(
        <img
          key={i}
          src={RatingStarUnclicked}
          alt="unclicked star"
          style={{ width: "1.8rem" }}
        />
      );
    }
  }

  return <div className="rating-container">{stars}</div>;
};

export default RatingStars;
