import React from 'react';
import "./InputField.css";

export default function InputField({label, type}) {
  return (
    <div className="InputFieldContainer">   
          <input type={type} placeholder={label} required></input>
      </div>
  )
}