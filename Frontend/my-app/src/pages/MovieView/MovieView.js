import React, { useEffect, useRef, useState } from "react";
import { MovieService } from "../../assets/service/movie_service";
import { useParams } from "react-router-dom";
import "./MovieView.css";
import FavoriteButton from "../../components/button/FavoriteButton";
import BackdropCard from "../../components/card/BackdropCard";
import LeftArrow from "../../assets/images/ButtonSVG.svg";
import RightArrow from "../../assets/images/ButtonSVGClose.svg";
import Tags from "../../components/tags/Tags";

export default function MovieView() {
  const { imdbId } = useParams();
  const [movie, setMovie] = useState({ backdrops: [] });
  const scrollRef = useRef(null);
  const [backgroundImage, setBackgroundImage] = useState("");

  useEffect(() => {
    const fetchMovie = async () => {
      try {
        const movieData = await new MovieService().getMovie(imdbId);
        setMovie(movieData);
      } catch (error) {
        console.error("Error fetching user me data:", error);
      }
    };
    if (imdbId) {
      fetchMovie();
    }
  }, [imdbId]);

  const handleBackdropClick = (img) => {
    setBackgroundImage(img);
  };

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
    <div
      className="MovieContainer"
      style={{
        backgroundImage: `linear-gradient(
          to bottom,
          rgb(6 15 23 / 33%),
          rgb(6 14 22 / 82%)
        ), url(${backgroundImage ? backgroundImage : movie.backdrops[0]})`,
        width: "100%",
      }}>
      <div className="content-container">
        <div className="play-container">
          <h1>{movie.title}</h1>

          <a href={movie.trailerLink} target="_blank" rel="noopener noreferrer">
            <button className="play-icon">
              <p>Watch</p>
            </button>
          </a>
          <FavoriteButton></FavoriteButton>
        </div>
        <div className="text-container">
          <hr />
          <div className="tags-container">
            {movie.genres?.map((obj, index) => {
              return <Tags key={index} name={obj} />;
            })}
          </div>

          <p>{movie.plot}</p>
        </div>

        <div className="img-view-container">
          {movie.backdrops?.length > 3 && (
            <button className="arrow arrow-left" onClick={scrollLeft}>
              <img src={LeftArrow} />
            </button>
          )}

          <div className="backdrop-container" ref={scrollRef}>
            {movie.backdrops?.map((obj, index) => (
              <BackdropCard
                key={index}
                img={obj}
                className="backdrop-card"
                onClick={() => handleBackdropClick(obj)}
              />
            ))}
          </div>
          {movie.backdrops?.length > 3 && (
            <button className="arrow arrow-right" onClick={scrollRight}>
              <img src={RightArrow} />
            </button>
          )}
        </div>
      </div>
    </div>
  );
}
