import React, { useEffect, useState } from "react";
import Card from "../../components/card/Card";
import { UserService } from "../../assets/service/user_service";
import Cookies from "js-cookie";
import "./FavoritePage.css";

export default function FavoritePage() {
  const [movies, setMovies] = useState([]);
  const email = Cookies.get("email");
  const password = Cookies.get("password");

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const userMeData = await new UserService().getUserMe(email, password);
        setMovies(userMeData.profile.favorites);
      } catch (error) {
        console.error("Error fetching user me data:", error);
      }
    };

    fetchMovies();
  }, []);

  return (
    <div className="FavoritePage">
      {movies.length > 0 ? (
        movies.map((obj) => (
          <Card
            key={obj.imdbId}
            id={obj.imdbId}
            poster={obj.poster}
            title={obj.title}></Card>
        ))
      ) : (
        <h3>No movies favored</h3>
      )}
    </div>
  );
}
