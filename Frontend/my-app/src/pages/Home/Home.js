import React, { useEffect, useRef, useState } from "react";
import Card from "../../components/card/Card";
import "./Home.css";
import { Searchbar } from "../../components/searchbar/Searchbar";
import GenreCard from "../../components/card/GenreCard";
import { MovieService } from "../../assets/service/movie_service";
import LeftArrow from "../../assets/images/ButtonSVG.svg";
import RightArrow from "../../assets/images/ButtonSVGClose.svg";
import {
  SkeletonGenreCard,
  SkeletonMovieCard,
  SkeletonRandomMovie,
} from "../../components/skeletonLoader/SkeletonLoader";

export default function Home() {
  const [movies, setMovies] = useState([]);
  const [bestRatedMovies, setBestRatedMovies] = useState([]);
  const [genre, setGenre] = useState([]);
  const [filteredMovies, setFilteredMovies] = useState([]);
  const [randomMovie, setRandomMovie] = useState(null);
  const scrollRef = useRef({});

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const movieData = await new MovieService().getMovies();
        setMovies(movieData);
        setFilteredMovies(movieData);
        setRandomMovie(movieData[Math.floor(Math.random() * movieData.length)]);
      } catch (error) {
        console.error("Error fetching movie data:", error);
      }
    };
    const fetchGenres = async () => {
      try {
        const genreData = await new MovieService().getGenre();
        setGenre(genreData);
      } catch (error) {
        console.error("Error fetching genre data:", error);
      }
    };

    const fetchBestRatedMovies = async () => {
      try {
        const movieData = await new MovieService().getBestRatedMovie();
        setBestRatedMovies(movieData);
      } catch (error) {
        console.error("Error fetching movie data:", error);
      }
    };
    fetchBestRatedMovies();
    fetchMovies();
    fetchGenres();
  }, []);

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (movies.length > 0) {
        setRandomMovie(movies[Math.floor(Math.random() * movies.length)]);
      }
    }, 20000);

    return () => clearInterval(intervalId);
  }, [movies]);

  const scrollLeft = (key) => {
    if (scrollRef.current[key]) {
      scrollRef.current[key].scrollBy({ left: -300, behavior: "smooth" });
    }
  };

  const scrollRight = (key) => {
    if (scrollRef.current[key]) {
      scrollRef.current[key].scrollBy({ left: 300, behavior: "smooth" });
    }
  };

  const handleSearch = (query) => {
    const filtered = movies.filter((movie) =>
      movie.title.toLowerCase().includes(query.toLowerCase())
    );
    setFilteredMovies(filtered);
  };

  return (
    <div className="Home">
      <div>
        <Searchbar movies={movies} onSearch={handleSearch} />
      </div>
      <div>
        {randomMovie ? (
          <div className="random-movie">
            <img src={randomMovie.poster} alt={randomMovie.title} />
          </div>
        ) : (
          <div className="random-movie">
            <SkeletonRandomMovie />
          </div>
        )}
      </div>
      <div>
        <h2>
          Kategorien<span>.</span>
        </h2>
        <div className="img-view-container">
          <button
            className="arrow arrow-left"
            onClick={() => scrollLeft("genres")}>
            <img src={LeftArrow} alt="Left Arrow" />
          </button>
          <div
            className="backdrop-container"
            ref={(el) => (scrollRef.current["genres"] = el)}>
            {Object.keys(genre).length > 0
              ? Object.keys(genre).map((obj) => (
                  <GenreCard key={obj} genre={obj}></GenreCard>
                ))
              : Array(5)
                  .fill(0)
                  .map((_, index) => <SkeletonGenreCard key={index} />)}
          </div>
          <button
            className="arrow arrow-right"
            onClick={() => scrollRight("genres")}>
            <img src={RightArrow} alt="Right Arrow" />
          </button>
        </div>
      </div>
      <div>
        <h2>
          Beliebte Filme<span>.</span>
        </h2>
        <div className="img-view-container">
          <button
            className="arrow arrow-left"
            onClick={() => scrollLeft("movies")}>
            <img src={LeftArrow} alt="Left Arrow" />
          </button>
          <div
            className="backdrop-container"
            ref={(el) => (scrollRef.current["movies"] = el)}>
            {bestRatedMovies.length > 0
              ? bestRatedMovies.map((obj) => (
                  <Card
                    key={obj.imdbId}
                    id={obj.imdbId}
                    poster={obj.poster}
                    title={obj.title}></Card>
                ))
              : Array(5)
                  .fill(0)
                  .map((_, index) => <SkeletonMovieCard key={index} />)}
          </div>
          <button
            className="arrow arrow-right"
            onClick={() => scrollRight("movies")}>
            <img src={RightArrow} alt="Right Arrow" />
          </button>
        </div>
      </div>
    </div>
  );
}
