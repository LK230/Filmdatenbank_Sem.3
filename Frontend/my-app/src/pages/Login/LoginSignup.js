import React, { useState } from "react";
import "./LoginSignup.css";
import InputField from "../../components/inputfield/InputField";
import ButtonComponent from "../../components/buttonComponent/ButtonComponent";

const LoginSignup = () => {
  const [isLogin, setIsLogin] = useState(true);

  return (
    <div className="container">
      <div className="tabs">
        <div
          className={`tab ${isLogin ? "active" : ""}`}
          onClick={() => setIsLogin(true)}>
          Login
        </div>
        <div
          className={`tab ${!isLogin ? "active" : ""}`}
          onClick={() => setIsLogin(false)}>
          Signup
        </div>
      </div>
      <div className="content-wrapper">
        <div className="form-container">
          {isLogin ? (
            <div className="form-content">
              <h1 className="header">Melde dich an</h1>
              <div className="content-container">
                <div className="form-group">
                  <InputField label="Email" />
                </div>
                <div className="form-group">
                  <InputField label="Passwort" type="password" />
                </div>
              </div>
              <div className="form-button">
                <ButtonComponent label="Anmelden" />
              </div>
            </div>
          ) : (
            <div className="form-content">
              <h1 className="header">Registriere dich</h1>
              <div className="form-group name-fields">
                <InputField label="Name" />
                <InputField label="Nachname" />
              </div>
              <div className="form-group">
                <InputField label="Email" />
              </div>
              <div className="form-group">
                <InputField label="Passwort erstellen" />
              </div>
              <div className="form-group">
                <InputField label="Passwort wiederholen" />
              </div>
              <div className="form-button">
                <ButtonComponent label="Abschicken" />
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default LoginSignup;
