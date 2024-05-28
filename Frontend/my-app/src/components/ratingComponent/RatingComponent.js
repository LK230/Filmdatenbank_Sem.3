import React, { useState } from "react";
import RatingStarUnclicked from "../../assets/images/Icons/RatingStarUnclicked.svg"
import RatingStarClicked from "../../assets/images/Icons/RatingStarClicked.svg"
import ButtonComponent from "../../components/buttonComponent/ButtonComponent"
import "./RatingComponent.css"


export default function RatingComponent(){
    const [rating, setRating] = useState(0);
    const [hoverRating, setHoverRating] = useState(0);
    const [inputCount, setInputCount] = useState("");

    function handleClick(value){
        setRating(value);
    }
    
    function handleMouseEnter(value){
        setHoverRating(value);
    }

    function handleMouseLeave(value){
        setHoverRating(0);
    }

    const handleInputChange = (e) => {
        setInputCount(e.target.value);
    }

    return(
        <div className="RatingContainer">
            <h1>Bewertung</h1>
            {[1, 2, 3, 4, 5].map((value) => {
                let imageSrc;
                if ((hoverRating || rating) >= value) {
                    imageSrc = RatingStarClicked;
                } else {
                    imageSrc = RatingStarUnclicked;
                }
                return (
                    <button onClick={() => handleClick(value)} onMouseEnter={() => handleMouseEnter(value)} onMouseLeave={handleMouseLeave}>
                        <img src={imageSrc} alt="Rating Star" />
                    </button>);
            })}

                <div className="CommentContainer">
                <textarea
                    placeholder= "Kommentar"
                    value={inputCount}
                    onChange={handleInputChange}
                    rows="5"
                    maxLength="60"
                />
                </div>
                <div className="CharacterCountContainer">
                    {inputCount.length}/60
                </div>
                <div><ButtonComponent label="Abschicken"></ButtonComponent></div>
        </div>
    )
    
}
