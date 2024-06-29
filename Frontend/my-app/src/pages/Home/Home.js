import React, { useEffect, useRef, useState } from "react";
import Card from "../../components/card/Card";
import "./Home.css";
import { Searchbar } from "../../components/searchbar/Searchbar";
import GenreCard from "../../components/card/GenreCard";
import { MovieService } from "../../assets/service/movie_service";
import LeftArrow from "../../assets/images/ButtonSVG.svg";
import RightArrow from "../../assets/images/ButtonSVGClose.svg";
import { UserService } from "../../assets/service/user_service";
import {
  SkeletonGenreCard,
  SkeletonMovieCard,
  SkeletonRandomMovie,
} from "../../components/skeletonLoader/SkeletonLoader";

export default function Home() {
  const [movies, setMovies] = useState([]);
  const [, /*filteredMovies*/ setFilteredMovies] = useState([]); // filteredMovies is not being used. Please fix!
  const [randomMovie, setRandomMovie] = useState(null);
  const scrollRef = useRef(null);

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const movieData = await new MovieService().getMovies();
        setMovies(movieData);
        setFilteredMovies(movieData);
        setRandomMovie(movieData[Math.floor(Math.random() * movieData.length)]);
      } catch (error) {
        console.error("Error fetching user me data:", error);
      }
    };

    fetchMovies();
  }, []);

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (movies.length > 0) {
        setRandomMovie(movies[Math.floor(Math.random() * movies.length)]);
      }
    }, 20000);

    return () => clearInterval(intervalId);
  }, [movies]);

  const scrollLeft = () => {
    if (scrollRef.current) {
      scrollRef.current.scrollBy({ left: -300, behavior: "smooth" });
    }
  };

  const scrollRight = () => {
    if (scrollRef.current) {
      scrollRef.current.scrollBy({ left: 300, behavior: "smooth" });
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
          <button className="arrow arrow-left" onClick={scrollLeft}>
            <img src={LeftArrow} alt="Left Arrow" />
          </button>
          <div className="backdrop-container" ref={scrollRef}>
            {movies.length > 0
              ? movies.map((obj, index) => (
                  <Card
                    key={index}
                    id={obj.imdbId}
                    poster={obj.poster}
                    title={obj.title}></Card>
                ))
              : Array(5)
                  .fill(0)
                  .map((_, index) => <SkeletonMovieCard key={index} />)}
          </div>
          <button className="arrow arrow-right" onClick={scrollRight}>
            <img src={RightArrow} alt="Right Arrow" />
          </button>
        </div>
      </div>
      <div>
        {movies.length > 0
          ? movies.map((obj, index) => (
              <GenreCard
                key={index}
                id={obj.genre}
                title={obj.title}></GenreCard>
            ))
          : Array(5)
              .fill(0)
              .map((_, index) => <SkeletonGenreCard key={index} />)}
      </div>
    </div>
  );
}
