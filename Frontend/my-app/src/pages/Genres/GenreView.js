import React, { useEffect, useState } from "react";
import Card from "../../components/card/Card";
import { MovieService } from "../../assets/service/movie_service";
import { SkeletonMovieCard } from "../../components/skeletonLoader/SkeletonLoader";
import { useParams } from "react-router-dom";
import "./GenreView.css";

export function GenreView() {
  const { genre } = useParams();
  const [genreMovie, setGenreMovie] = useState({});

  useEffect(() => {
    const fetchGenresAndMovies = async () => {
      try {
        const movies = await new MovieService().getGenreMovies(genre);
        setGenreMovie(movies);
      } catch (error) {
        console.error("Fehler beim Abrufen der Genres und Filme:", error);
      }
    };
    fetchGenresAndMovies();
  }, []);

  return (
    <div className="GenreView">
      <h2>
        {genre}
        <span>.</span>
      </h2>

      <div className="backdrop-container">
        {genreMovie.length > 0
          ? genreMovie.map((movie, index) => (
              <Card
                key={index}
                id={movie.imdbId}
                poster={movie.poster}
                title={movie.title}
              />
            ))
          : Array(5)
              .fill(0)
              .map((_, index) => <SkeletonMovieCard key={index} />)}
      </div>
    </div>
  );
}
