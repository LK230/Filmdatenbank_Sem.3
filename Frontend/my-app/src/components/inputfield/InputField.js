import React from "react";
import "./InputField.css";

export default function InputField({ label, type, onChange }) {
  return (
    <div className="InputFieldContainer">
      <input
        type={type}
        placeholder={label}
        required
        onChange={onChange}></input>
    </div>
  );
}
