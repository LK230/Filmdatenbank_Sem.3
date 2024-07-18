import React, { useState } from "react";
import "./LoginSignup.css";
import InputField from "../../components/inputfield/InputField";
import ButtonComponent from "../../components/buttonComponent/ButtonComponent";
import { UserService } from "../../assets/service/user_service";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import Alert from "../../components/alert/Alert";

const LoginSignup = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [username, setUsername] = useState("");
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const userService = new UserService();
  const navigate = useNavigate();
  const [alertMessage, setAlertMessage] = useState("");
  const [alertType, setAlertType] = useState("");

  const handleSubmit = async () => {
    try {
      if (isLogin) {
        const login = await userService.getUsers(email, password);
        if (login === "Login successfully!") {
          Cookies.set("email", email, { expires: 7 });
          Cookies.set("password", password, { expires: 7 });
          setAlertMessage("Login erfolgreich!");
          setAlertType("success");
          setTimeout(() => {
            navigate("/");
          }, 2000);
        } else {
          setAlertMessage("Unbekannter Fehler beim Login.");
          setAlertType("error");
        }
      } else {
        const newUser = {
          name: name,
          surname: surname,
          username: username,
          password: password,
          email: email,
        };
        const signup = await userService.createUser(newUser);
        if (signup === "User was created successfully!") {
          Cookies.set("email", email, { expires: 7 });
          setAlertMessage("Registrierung erfolgreich!");
          setAlertType("success");
          setTimeout(() => {
            navigate("/");
          }, 2000);
        } else {
          setAlertMessage("Unbekannter Fehler bei der Registrierung.");
          setAlertType("error");
        }
      }
    } catch (error) {
      console.error("Error during submission:", error);
      if (error === "Wrong email or password") {
        setAlertMessage(
          error.message || "Falsche Email-Adresse oder Passwort."
        );
        setAlertType("error");
      } else if (
        error === "The entered e-mail address or username is already taken"
      ) {
        setAlertMessage(
          error.message ||
            "Die eingegebene Email-Adresse oder der Username ist bereits vergeben."
        );
        setAlertType("error");
      } else if (error === "The entered e-mail address is not valid") {
        setAlertMessage(
          error.message || "Die eingegebene Email-Adresse ist nicht gültig."
        );
        setAlertType("error");
      }
    }
  };

  return (
    <div className="LoginContainer">
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
                <div className="form-group"></div>
                <div className="form-group">
                  <InputField
                    label="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <div className="form-group">
                  <InputField
                    label="Passwort"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </div>
              </div>
              <div className="form-button">
                <ButtonComponent label="Anmelden" onClick={handleSubmit} />
              </div>
            </div>
          ) : (
            <div className="form-content">
              <div>
                <div className="header-container">
                  <h1 className="header">Registriere dich</h1>
                </div>
                <div className="form-group name-fields">
                  <InputField
                    label="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                  />
                  <InputField
                    label="Nachname"
                    value={surname}
                    onChange={(e) => setSurname(e.target.value)}
                  />
                </div>
                <div className="form-group">
                  <InputField
                    label="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                  />
                </div>
                <div className="form-group">
                  <InputField
                    label="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <div className="form-group">
                  <InputField
                    label="Passwort"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </div>
              </div>

              <div className="form-button">
                <ButtonComponent label="Abschicken" onClick={handleSubmit} />
              </div>
            </div>
          )}
        </div>
      </div>
      <Alert message={alertMessage} type={alertType} />
    </div>
  );
};

export default LoginSignup;
