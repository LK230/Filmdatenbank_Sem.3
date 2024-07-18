import React, { useEffect, useState, useRef } from "react";
import { MovieService } from "../../assets/service/movie_service";
import Card from "../../components/card/Card";
import LeftArrow from "../../assets/images/ButtonSVG.svg";
import RightArrow from "../../assets/images/ButtonSVGClose.svg";
import "./Genres.css";
import {
  SkeletonGenreCard,
  SkeletonMovieCard,
} from "../../components/skeletonLoader/SkeletonLoader";

export default function Genres() {
  const [genre, setGenre] = useState({});
  const [genreMovie, setGenreMovie] = useState({});
  const scrollRefs = useRef({});

  useEffect(() => {
    const fetchGenresAndMovies = async () => {
      try {
        const genreData = await new MovieService().getGenre();
        setGenre(genreData);

        const moviesForGenres = {};
        for (const genreName of Object.keys(genreData)) {
          const movies = await new MovieService().getGenreMovies(genreName);
          moviesForGenres[genreName] = movies;
        }
        setGenreMovie(moviesForGenres);
      } catch (error) {
        console.error("Fehler beim Abrufen der Genres und Filme:", error);
      }
    };
    fetchGenresAndMovies();
  }, []);

  const scrollLeft = (genreName) => {
    if (scrollRefs.current[genreName]) {
      scrollRefs.current[genreName].scrollBy({
        left: -300,
        behavior: "smooth",
      });
    }
  };

  const scrollRight = (genreName) => {
    if (scrollRefs.current[genreName]) {
      scrollRefs.current[genreName].scrollBy({ left: 300, behavior: "smooth" });
    }
  };

  return (
    <div className="Genres">
      <div>
        {Object.keys(genre).length > 0
          ? Object.keys(genre).map((genreName) => (
              <div key={genreName}>
                <h2>
                  {genreName}
                  <span>.</span>
                </h2>
                <div>
                  <div className="img-view-container">
                    {genreMovie[genreName]?.length > 3 ? (
                      <button
                        className="arrow arrow-left"
                        onClick={() => scrollLeft(genreName)}>
                        <img src={LeftArrow} alt="Left Arrow" />
                      </button>
                    ) : (
                      <div className="arrow-margin"> </div>
                    )}

                    <div
                      className="backdrop-container"
                      ref={(el) => (scrollRefs.current[genreName] = el)}>
                      {genreMovie[genreName]?.length > 0
                        ? genreMovie[genreName].map((movie, index) => (
                            <Card
                              key={index}
                              id={movie.imdbId}
                              poster={movie.poster}
                              title={movie.title}
                            />
                          ))
                        : Array(5)
                            .fill(0)
                            .map((_, index) => (
                              <SkeletonMovieCard key={index} />
                            ))}
                    </div>
                    {genreMovie[genreName]?.length > 3 && (
                      <button
                        className="arrow arrow-right"
                        onClick={() => scrollRight(genreName)}>
                        <img src={RightArrow} alt="Right Arrow" />
                      </button>
                    )}
                  </div>
                </div>
              </div>
            ))
          : Array(5)
              .fill(0)
              .map((_, index) => <SkeletonGenreCard key={index} />)}
      </div>
    </div>
  );
}
