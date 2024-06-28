import React, { useState, useEffect } from "react";
import "./FavoriteButton.css";

import FavoriteButtonClicked from "../../assets/images/icons/FavoriteButtonClicked.svg";
import FavoriteButtonUnclicked from "../../assets/images/icons/FavoriteButtonUnclicked.svg";

export default function FavoriteButton({onClick, isActive}) {
  const [isFavored, setIsFavored] = useState(isActive);

  useEffect(() => {
    setIsFavored(isActive);
  }, [isActive]);

  const handleClick = () => {
    setIsFavored(!isFavored);
    onClick();
  };

  return (
    <div className="FavoriteButtonContainer">
      <button onClick={handleClick}>
        {isFavored ? (
          <img src={FavoriteButtonClicked} alt="Favorite Button Clicked" />
        ) : (
          <img src={FavoriteButtonUnclicked} alt="Favorite Button Unclicked" />
        )}
      </button>
    </div>
  );
}

