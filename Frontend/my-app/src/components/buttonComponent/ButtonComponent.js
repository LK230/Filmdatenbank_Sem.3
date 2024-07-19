import React from "react";
import ButtonSVGClose from "../../assets/images/ButtonSVGClose.svg";
import ButtonDisabled from "../../assets/images/ButtonDisabled.svg";

import "./ButtonComponent.css";

export default function ButtonComponent({ label, onClick, disabled }) {
  return (
    <div className="ButtonComponentContainer">
      <button onClick={onClick} disabled={disabled}>
        <div className="ButtonComponent">
          <p>{label}</p>
          {disabled ? (
            <img src={ButtonDisabled} alt="open-close Button" />
          ) : (
            <img src={ButtonSVGClose} alt="open-close Button" />
          )}
        </div>
      </button>
    </div>
  );
}
