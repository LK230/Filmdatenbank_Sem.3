import React from "react";
import "./Tags.css";

export default function Tags({ name }) {
  return (
    <div className="Tag-container">
      <p>{name}</p>
    </div>
  );
}
