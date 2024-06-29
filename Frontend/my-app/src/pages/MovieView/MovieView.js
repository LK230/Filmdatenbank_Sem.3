import React, { useEffect, useRef, useState } from "react";
import { MovieService } from "../../assets/service/movie_service";
import { useParams } from "react-router-dom";
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

export default function MovieView() {
  const { imdbId } = useParams();
  const [movie, setMovie] = useState({ backdrops: [] });
  const scrollRef = useRef(null);
  const [backgroundImage, setBackgroundImage] = useState("");
  const [user, setUser] = useState(null);
  const [isFavored, setIsFavored] = useState(false);

  const getAddToFavorites = async () => {
    try {
      const email = Cookies.get("email");
      const response = await new UserService().userAddToFavorite(email, imdbId);
      console.log("response", response);
    } catch (error) {
      console.error("Error adding to favorites:", error);
      throw error;
    }
  };

  // const getDeleteFromFavorites = async (user, imdbId) => {
  //   try {
  //     const response = await new UserService().userDeleteFromFavorite(
  //       user.username,
  //       imdbId
  //     );
  //     return response;
  //   } catch (error) {
  //     console.error("Error deleting from favorites:", error);
  //     throw error;
  //   }
  // };

  // const fetchUser = async () => {
  //   try {
  //     const email = Cookies.get("email");
  //     const password = Cookies.get("password");
  //     const userdata = await new UserService().getUsers(email, password);
  //     console.log("USER", userdata);

  //     setUser(userdata);
  //   } catch (error) {
  //     console.error("Error fetching user me data:", error);
  //   }
  // };

  useEffect(() => {
    const fetchMovie = async () => {
      try {
        const movieData = await new MovieService().getMovie(imdbId);
        console.log("movieData", movieData);
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

  const handleFavMovie = () => {
    if (
      user &&
      user.favorites &&
      user.favorites.some((obj) => obj.imdbId === imdbId)
    ) {
      //wenn obj id dann delete
      // getDeleteFromFavorites(user, imdbId);
      // setUser((prevUser) => ({
      //   ...prevUser,
      //   favorites: prevUser.favorites.filter((fav) => fav.imdbId !== imdbId),
      // }));
    } else {
      // getAddToFavorites(user, imdbId); //sonst add
      // setUser((prevUser) => ({
      //   ...prevUser,
      //   favorites: [...prevUser.favorites, { imdbId }],
      // }));
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
          <a href={movie.trailerLink} target="_blank" rel="noopener noreferrer">
            <button className="play-icon">
              <p>Watch</p>
            </button>
          </a>
          <FavoriteButton
            onClick={getAddToFavorites}
            isActive={isFavored}></FavoriteButton>
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
      </div>
      <div className="review-container">
        <div>
          <h2>Bewertungen</h2>
          {movie.reviewIds?.map((obj) => {
            return (
              <RatingView
                user={obj.createdBy}
                comment={obj.body}
                rating={obj.rating}
              />
            );
          })}
        </div>
      </div>
    </div>
  );
}
