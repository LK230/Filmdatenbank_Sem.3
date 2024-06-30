import React from "react";
import "./InputField.css";

export default function InputField({ label, value, type, onChange, onKeyDown, disabled }) {
  return (
    <div className="InputFieldContainer">
      <input
        type={type}
        placeholder={label}
        value={value}
        required
        onChange={onChange}
        onKeyDown={onKeyDown}
        disabled={disabled}
      >
        </input>
    </div>
  );
}
