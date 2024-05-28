import React from "react";
import ButtonSVGClose from "../../assets/images/ButtonSVGClose.svg";
import "./ButtonComponent.css";

export default function ButtonComponent({ label }) {
  return (
    <div className="ButtonComponentContainer">
      <button>
        <div className="ButtonComponent">
          <p>{label}</p>

          <img src={ButtonSVGClose} alt="open-close Button" />
        </div>
      </button>
    </div>
  );
}
