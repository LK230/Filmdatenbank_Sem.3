import React, { useState } from "react";
import "./FavoriteButton.css";

import FavoriteButtonClicked from "../../assets/images/icons/FavoriteButtonClicked.svg";

import FavoriteButtonUnclicked from "../../assets/images/icons/FavoriteButtonUnclicked.svg";

export default function FavoriteButton() {
  const [isFavored, setIsFavored] = useState(false);

  function favored() {
    setIsFavored(!isFavored);
  }

  return (
    <div className="FavoriteButtonContainer">
      <button onClick={favored}>
        {isFavored ? (
          <img src={FavoriteButtonClicked} alt="Favorite Button Clicked" />
        ) : (
          <img src={FavoriteButtonUnclicked} alt="Favorite Button Unclicked" />
        )}
      </button>
    </div>
  );
}
