import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Card from "../../components/card/Card";
import api from "../../assets/api/axiosConfig";
import "./Home.css";

export default function Home() {
  const [movies, setMovies] = useState([]);

  const getMovies = async () => {
    try {
      const response = await api.get("/movies");
      console.log("response", response.data);
      setMovies(response.data);
    } catch (err) {
      console.log("err", err);
    }
  };

  useEffect(() => {
    getMovies();
  }, []);
  return (
    <div className="Home">
      <h1>HOME</h1>
      <button>
        <Link to="/genres">Go to Genres</Link>
      </button>
      <div className="card">
        {movies.map((obj, index) => {
          return (
            <Card id={obj.imdbId} poster={obj.poster} title={obj.title}></Card>
          );
        })}
      </div>
    </div>
  );
}
