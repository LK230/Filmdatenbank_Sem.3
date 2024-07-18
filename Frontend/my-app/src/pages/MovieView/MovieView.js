import React, { useEffect, useRef, useState } from "react";
import { MovieService } from "../../assets/service/movie_service";
import { useParams, Link } from "react-router-dom";
import "./MovieView.css";
import FavoriteButton from "../../components/button/FavoriteButton";
import BackdropCard from "../../components/card/BackdropCard";
import LeftArrow from "../../assets/images/ButtonSVG.svg";
import RightArrow from "../../assets/images/ButtonSVGClose.svg";
import Tags from "../../components/tags/Tags";
import {
  SkeletonMovieCard,
  SkeletonTitle,
} from "../../components/skeletonLoader/SkeletonLoader";
import RatingView from "../../components/showRatingView/RatingView";
import { UserService } from "../../assets/service/user_service";
import Cookies from "js-cookie";
import Rated from "../../components/rated/Rated";
import RatingComponent from "../../components/ratingComponent/RatingComponent";
import RatingStars from "../../components/showRatingView/RatingStars";
import { ReviewService } from "../../assets/service/review_service";

export default function MovieView() {
  const { imdbId } = useParams();
  const [movie, setMovie] = useState({ backdrops: [] });
  const scrollRef = useRef(null);
  const [backgroundImage, setBackgroundImage] = useState("");
  const [isFavored, setIsFavored] = useState(false);
  const email = Cookies.get("email");
  const password = Cookies.get("password");

  const getAddToFavorites = async () => {
    try {
      const response = await new UserService().userAddToFavorite(email, imdbId);
      if (response === "Movie added to favorites successfully") {
        setIsFavored(true);
      }
    } catch (error) {
      console.error("Error adding to favorites:", error);
      throw error;
    }
  };

  const getDeleteFromFavorites = async () => {
    try {
      const response = await new UserService().userDeleteFromFavorite(
        email,
        imdbId
      );
      if (response === "Movie removed from favorites successfully") {
        setIsFavored(false);
      }
    } catch (error) {
      console.error("Error deleting from favorites:", error);
      throw error;
    }
  };

  useEffect(() => {
    const fetchUserFavorites = async () => {
      try {
        const userMe = await new UserService().getUserMe(email, password);
        if (
          userMe &&
          userMe.profile.favorites &&
          userMe.profile.favorites.some((obj) => obj.imdbId === imdbId)
        ) {
          setIsFavored(true);
        } else {
          setIsFavored(false);
        }
      } catch (error) {
        console.error("Error fetching user favorites:", error);
      }
    };
    fetchUserFavorites();
  }, []);

  useEffect(() => {
    const fetchMovie = async () => {
      try {
        const movieData = await new MovieService().getMovie(imdbId);
        setMovie(movieData);
        if (movieData.backdrops && movieData.backdrops.length > 0) {
          setBackgroundImage(movieData.backdrops[0]);
        }
      } catch (error) {
        console.error("Error fetching movie data:", error);
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

  const handleFavMovie = async () => {
    const userMe = await new UserService().getUserMe(email, password);

    if (
      userMe &&
      userMe.profile.favorites &&
      userMe.profile.favorites.some((obj) => obj.imdbId === imdbId)
    ) {
      getDeleteFromFavorites();
    } else {
      getAddToFavorites();
    }
  };

  console.log("movie", movie);
  console.log(
    "REVIEWID",
    movie.reviewIds?.map((obj) => obj.id)
  );

  const handleDeleteReview = async (reviewId) => {
    try {
      console.log("reviewId", reviewId);
      const deleteReview = await new ReviewService().deleteReview(reviewId);
      console.log("delete", deleteReview);
    } catch (error) {
      console.log("Fehler beim Löschen der Bewertung", error);
    }
  };

  return (
    <div
      className="MovieContainer"
      style={{
        backgroundImage: movie
          ? `linear-gradient(
          to bottom,
          rgb(6 15 23 / 33%),
          rgb(6 14 22 / 82%)
        ), url(${backgroundImage})`
          : "none",
        width: "100%",
      }}>
      <div className="content-container">
        <div className="play-container">
          {movie ? <h1>{movie.title}</h1> : <SkeletonTitle />}
          <div className="right-side">
            <Rated age={movie.rated}></Rated>
            <a
              href={movie.trailerLink}
              target="_blank"
              rel="noopener noreferrer">
              <button className="play-icon">
                <p>Watch</p>
              </button>
            </a>
            {email && (
              <FavoriteButton
                onClick={handleFavMovie}
                isActive={isFavored}></FavoriteButton>
            )}
          </div>
        </div>
        <div className="show-rate-content">
          <p>{movie.rating?.toFixed(1).replace(".", ",")}</p>
          <RatingStars rating={movie.rating}></RatingStars>
        </div>
        <div className="text-container">
          <hr />
          <div className="tags-container">
            {movie.genres?.map((genre) => (
              <Link
                key={genre}
                to={`/movies/genreview/${genre}`}
                className="link">
                <Tags name={genre} />
              </Link>
            ))}
          </div>

          <div className="img-view-container">
            {movie.backdrops?.length > 3 && (
              <button className="arrow arrow-left" onClick={scrollLeft}>
                <img src={LeftArrow} alt="" />
              </button>
            )}

            <div className="backdrop-container" ref={scrollRef}>
              {movie
                ? movie.backdrops?.map((img, index) => (
                    <BackdropCard
                      key={index}
                      img={img}
                      className="backdrop-card"
                      onClick={() => handleBackdropClick(img)}
                    />
                  ))
                : Array(5)
                    .fill(0)
                    .map((_, index) => (
                      <div key={index} className="backdrop-card">
                        <SkeletonMovieCard />
                      </div>
                    ))}
            </div>
            {movie.backdrops?.length > 3 && (
              <button className="arrow arrow-right" onClick={scrollRight}>
                <img src={RightArrow} alt="" />
              </button>
            )}
          </div>

          <div className="info-content-container">
            <div className="plot">
              <p>{movie.plot}</p>
            </div>

            <div>
              <table>
                <tr>
                  <th>
                    <p>Unter der Regie von</p>
                  </th>
                  <td>
                    <p>{movie.director}</p>
                  </td>
                </tr>
                <tr>
                  <th>
                    <p>Besetzung</p>
                  </th>
                  <td>
                    <ul>
                      {movie.actors?.map((obj) => {
                        return <li>{obj}</li>;
                      })}
                    </ul>
                  </td>
                </tr>
                <tr>
                  <th>
                    <p>Veröffentlicht am</p>
                  </th>
                  <td>
                    {new Date(movie.releaseDate).toLocaleDateString("de-DE", {
                      day: "2-digit",
                      month: "long",
                      year: "numeric",
                    })}
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>

      <div className="review-container">
        <div className="RaitingComponentContainer">
          {email && (
            <div>
              <RatingComponent user={email} imdbId={imdbId} />
            </div>
          )}
        </div>
        <div className="ReviewsContainer">
          <h2>Bewertungen von anderen Usern</h2>
          {movie.reviewIds?.length > 0 ? (
            movie.reviewIds?.map((obj) => {
              return (
                <RatingView
                  user={obj.createdBy}
                  comment={obj.body}
                  rating={obj.rating}
                  onClick={() => handleDeleteReview(obj.id)}
                />
              );
            })
          ) : (
            <h3>Noch keine Bewertungen vorhanden</h3>
          )}
        </div>
      </div>
    </div>
  );
}
