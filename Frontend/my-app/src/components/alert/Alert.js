import React from "react";
import "./Alert.css";

export default function Alert({ message, type }) {
  if (!message) return null;

  return <div className={`alert ${type}`}>{message}</div>;
}
