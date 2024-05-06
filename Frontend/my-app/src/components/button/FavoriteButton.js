import React, { useState }  from 'react'
import "./FavoriteButton.css";

import FavoriteButtonClicked from "../../assets/images/Icons/FavoriteButtonClicked.svg";

import FavoriteButtonUnclicked from "../../assets/images/Icons/FavoriteButtonUnclicked.svg";



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
