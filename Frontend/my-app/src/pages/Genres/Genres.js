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
  const [movies, setMovies] = useState([]);
  const [genre, setGenre] = useState([]);
  const [genreMovie, setGenreMovie] = useState({});
  const scrollRefs = useRef({});

  useEffect(() => {
    const fetchGenres = async () => {
      try {
        const genreData = await new MovieService().getGenre();
        setGenre(genreData);
      } catch (error) {
        console.error("Fehler beim Abrufen der Genres:", error);
      }
    };
    fetchGenres();
  }, []);

  useEffect(() => {
      const fetchGenreMovies = async () => {
        try {
          const genreMovieData = await new MovieService().getGenreMovies("Action");
          setGenreMovie(genreMovieData);
        } catch (error) {
          console.error("Fehler beim Abrufen der GenreMovies:", error);
        }
      };
      fetchGenreMovies();
    }
  , []);

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

  const scrollLeft = (genre) => {
    if (scrollRefs.current[genre]) {
      scrollRefs.current[genre].scrollBy({ left: -300, behavior: "smooth" });
    }
  };

  const scrollRight = (genre) => {
    if (scrollRefs.current[genre]) {
      scrollRefs.current[genre].scrollBy({ left: 300, behavior: "smooth" });
    }
  };
console.log(genreMovie)
  return (
    <div className="Genres">
      <div>
        {Object.keys(genre).length > 0
          ? Object.keys(genre).map((genre) => (
              <div key={genre}>
                <h2>
                  {genre}
                  <span>.</span>
                </h2>
                <div>
                  <div className="img-view-container">
                    <button className="arrow arrow-left" onClick={() => scrollLeft(genre)}>
                      <img src={LeftArrow} alt="Left Arrow" />
                    </button>
                    <div
                      className="backdrop-container"
                      ref={(el) => (scrollRefs.current[genre] = el)}
                    >
                      {genreMovie.length > 0
                        ? genreMovie.map((obj, index) => (
                            <Card
                              key={index}
                              id={obj.imdbId}
                              poster={obj.poster}
                              title={obj.title}
                            />
                          ))
                        : Array(5)
                            .fill(0)
                            .map((_, index) => <SkeletonMovieCard key={index} />)}
                    </div>
                    <button className="arrow arrow-right" onClick={() => scrollRight(genre)}>
                      <img src={RightArrow} alt="Right Arrow" />
                    </button>
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
