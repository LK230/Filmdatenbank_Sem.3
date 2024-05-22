import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Card from "../../components/card/Card";
import "./Home.css";
import { Searchbar } from "../../components/searchbar/Searchbar";
import GenreCard from "../../components/card/GenreCard";
import { MovieService } from "../../assets/service/movie_service";

export default function Home() {
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const movieData = await new MovieService().getMovies();
        setMovies(movieData);
      } catch (error) {
        console.error("Error fetching user me data:", error);
      }
    };

    fetchMovies();
  }, []);

  return (
    <div className="Home">
      <h1>HOME</h1>
      <div>
        {" "}
        <Searchbar></Searchbar>
      </div>
      <button>
        <Link to="/movies/genres/${obj.genre}">Go to Genres</Link>
      </button>
      <div className="card">
        {movies.map((obj, index) => {
          return (
            <Card id={obj.imdbId} poster={obj.poster} title={obj.title}></Card>
          );
        })}
      </div>
      <div>
        {movies.map((obj, index) => {
          return (
            <GenreCard key={index} id={obj.genre} title={obj.title}></GenreCard>
          );
        })}
      </div>
    </div>
  );
}
