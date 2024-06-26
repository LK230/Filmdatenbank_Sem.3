import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Searchbar.css";
import { FaSearch } from "react-icons/fa";
import FavoriteButton from "../button/FavoriteButton";

export const Searchbar = ({ movies, onSearch }) => {
    const [inputValue, setInputValue] = useState("");
    const [placeholder, setPlaceholder] = useState("Suche");
    const [suggestions, setSuggestions] = useState([]);

    const handleInputChange = (e) => {
        const value = e.target.value;
        setInputValue(value);
        if (value) {
            const filteredSuggestions = movies.filter(movie =>
                movie.title.toLowerCase().includes(value.toLowerCase())
            );
            setSuggestions(filteredSuggestions);
        } else {
            setSuggestions([]);
        }
    };

    const handleInputClick = () => {
        if (placeholder === "Suche") {
            setPlaceholder("");
        }
    };

    const handleInputBlur = () =>{
        if (inputValue === ""){
            setPlaceholder("Suche");
        }
    };

    const handleSearchSubmit = () => {
        if (inputValue) {
            onSearch(inputValue);
            setSuggestions([]);
        }
    };

    const handleFavoriteClick = (e) => {
        e.preventDefault();
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
                    onBlur={handleInputBlur}
                    onKeyDown={(e) => e.key === 'Enter' && handleSearchSubmit()}
                />
            </div>
            {suggestions.length > 0 && (
                <div className="suggestions">
                    {suggestions.map((movie, index) => (
                     <Link key={index} to={`/movies/${movie.imdbId}`} className="suggestion-item">
                    <img src={movie.poster} alt={movie.title} />
                     <span>{movie.title}</span>
                    <button onClick={handleFavoriteClick}><FavoriteButton></FavoriteButton></button>
                     </Link>
                ))} 
                </div>
            )}
            
        
        </div>
    );
};
