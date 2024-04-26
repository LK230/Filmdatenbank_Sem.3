import React from "react";
import { Link } from "react-router-dom";
import Card from "../../components/card/Card";

export default function Home() {
  return (
    <div>
      <h1>HOME</h1>
      <button>
        <Link to="/genres">Go to Genres</Link>
      </button>
      <div><Card></Card></div>
    </div>
  );
}
