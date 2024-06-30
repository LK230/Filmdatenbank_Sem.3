import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Searchbar.css";
import { FaSearch } from "react-icons/fa";
import FavoriteButton from "../button/FavoriteButton";
import { UserService } from "../../assets/service/user_service";
import Cookies from "js-cookie";

export const Searchbar = ({ movies, onSearch }) => {
  const [inputValue, setInputValue] = useState("");
  const [placeholder, setPlaceholder] = useState("Suche");
  const [suggestions, setSuggestions] = useState([]);
  const [favoredMovies, setFavoredMovies] = useState(new Set());

  const email = Cookies.get("email");
  const password = Cookies.get("password");

  useEffect(() => {
    const fetchFavorites = async () => {
      try {
        const userMe = await new UserService().getUserMe(email, password);
        if (userMe && userMe.profile.favorites) {
          const favoriteIds = userMe.profile.favorites.map((fav) => fav.imdbId);
          setFavoredMovies(new Set(favoriteIds));
        }
      } catch (error) {
        console.error("Error fetching favorite movies:", error);
      }
    };

    fetchFavorites();
  }, [email, password]);

  const handleInputChange = (e) => {
    const value = e.target.value;
    setInputValue(value);
    if (value) {
      const filteredSuggestions = movies.filter((movie) =>
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

  const handleInputBlur = () => {
    if (inputValue === "") {
      setPlaceholder("Suche");
    }
  };

  const handleSearchSubmit = () => {
    if (inputValue) {
      onSearch(inputValue);
      setSuggestions([]);
    }
  };

  const handleFavorite = async (imdbId) => {
    if (favoredMovies.has(imdbId)) {
      await getDeleteFromFavorites(imdbId);
    } else {
      await getAddToFavorites(imdbId);
    }
  };

  const getAddToFavorites = async (imdbId) => {
    try {
      const response = await new UserService().userAddToFavorite(email, imdbId);
      if (response === "Movie added to favorites successfully") {
        setFavoredMovies((prev) => new Set(prev).add(imdbId));
      }
    } catch (error) {
      console.error("Error adding to favorites:", error);
    }
  };

  const getDeleteFromFavorites = async (imdbId) => {
    try {
      const response = await new UserService().userDeleteFromFavorite(
        email,
        imdbId
      );
      if (response === "Movie removed from favorites successfully") {
        setFavoredMovies((prev) => {
          const newSet = new Set(prev);
          newSet.delete(imdbId);
          return newSet;
        });
      }
    } catch (error) {
      console.error("Error deleting from favorites:", error);
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
          onBlur={handleInputBlur}
          onKeyDown={(e) => e.key === "Enter" && handleSearchSubmit()}
        />
      </div>
      {suggestions.length > 0 && (
        <div className="suggestions">
          {suggestions.map((movie) => (
            <div key={movie.imdbId} className="suggestion-item">
              <Link to={`/movies/${movie.imdbId}`}>
                <img src={movie.poster} alt={movie.title} />
                <span>{movie.title}</span>
              </Link>
              <div className="favoriteButton">
                <FavoriteButton
                  onClick={() => handleFavorite(movie.imdbId)}
                  isActive={favoredMovies.has(movie.imdbId)}
                />
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};
