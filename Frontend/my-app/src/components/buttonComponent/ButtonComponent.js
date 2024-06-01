import React from "react";
import ButtonSVGClose from "../../assets/images/ButtonSVGClose.svg";
import "./ButtonComponent.css";

export default function ButtonComponent({ label, onClick }) {
  return (
    <div className="ButtonComponentContainer">
      <button onClick={onClick}>
        <div className="ButtonComponent">
          <p>{label}</p>

          <img src={ButtonSVGClose} alt="open-close Button" />
        </div>
      </button>
    </div>
  );
}
