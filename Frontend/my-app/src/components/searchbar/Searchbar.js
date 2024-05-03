import React, { useState } from "react";
import "./Searchbar.css";
import { FaSearch } from "react-icons/fa";

export const Searchbar = () => {
    const [inputValue, setInputValue] = useState("");
    const [placeholder, setPlaceholder] = useState("Suche");

    const handleInputChange = (e) => {
        setInputValue(e.target.value);
    };

    const handleInputClick = () => {
        if (placeholder === "Suche") {
            setPlaceholder("");
        }
    };

    return ( 
        <div className="SearchbarContainer">
            <div className="Searchbar">
                <FaSearch id="search-icon" />
                <input
                    placeholder={placeholder}
                    value={inputValue}
                    onChange={handleInputChange}
                    onClick={handleInputClick}
                />
            </div>
        </div>
    );
}


