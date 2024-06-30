import React from "react";
import "./InputField.css";

export default function InputField({ label, type, onChange, onKeyDown, disabled }) {
  return (
    <div className="InputFieldContainer">
      <input
        type={type}
        placeholder={label}
        required
        onChange={onChange}
        onKeyDown={onKeyDown}
        disabled={disabled}
      >
        </input>
    </div>
  );
}
