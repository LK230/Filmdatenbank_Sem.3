import React, { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import Card from "../../components/card/Card";
import "./Home.css";
import { Searchbar } from "../../components/searchbar/Searchbar";
import GenreCard from "../../components/card/GenreCard";
import { MovieService } from "../../assets/service/movie_service";
import LeftArrow from "../../assets/images/ButtonSVG.svg";
import RightArrow from "../../assets/images/ButtonSVGClose.svg";

export default function Home() {
  const [movies, setMovies] = useState([]);
  const [randomMovie, setRandomMovie] = useState(null);
  const scrollRef = useRef(null);

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const movieData = await new MovieService().getMovies();
        setMovies(movieData);
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
    }, 20000); // 20 Sekunden Intervall

    return () => clearInterval(intervalId); // Cleanup Intervall beim Demontieren
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

  return (
    <div className="Home">
      <div>
        <Searchbar></Searchbar>
      </div>
      <div>
        {randomMovie && (
          <div className="random-movie">
            <img src={randomMovie.poster} alt={randomMovie.title} />
          </div>
        )}
      </div>

      <div>
        <h2>
          Kategorien<span>.</span>
        </h2>
        <div className="img-view-container">
          <button className="arrow arrow-left" onClick={scrollLeft}>
            <img src={LeftArrow} />
          </button>

          <div className="backdrop-container" ref={scrollRef}>
            {movies.map((obj, index) => (
              <Card
                id={obj.imdbId}
                poster={obj.poster}
                title={obj.title}></Card>
            ))}
          </div>
          <button className="arrow arrow-right" onClick={scrollRight}>
            <img src={RightArrow} />
          </button>
        </div>
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
