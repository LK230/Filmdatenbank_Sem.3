import React, { useEffect, useState } from "react";
import Card from "../../components/card/Card";
import { UserService } from "../../assets/service/user_service";
import Cookies from "js-cookie";

export default function FavoritePage() {
  const [movies, setMovies] = useState([]);
  const email = Cookies.get("email");
  const password = Cookies.get("password");

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const userMeData = await new UserService().getUserMe(email, password);
        console.log("userMeData", userMeData.profile.favorites);
        setMovies(userMeData.profile.favorites);
      } catch (error) {
        console.error("Error fetching user me data:", error);
      }
    };

    fetchMovies();
  }, []);

  console.log("movies", movies);
  return (
    <div>
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
