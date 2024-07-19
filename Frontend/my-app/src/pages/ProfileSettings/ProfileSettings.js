import React, { useEffect, useState } from "react";
import "./ProfileSettings.css";
import InputField from "../../components/inputfield/InputField";
import { UserService } from "../../assets/service/user_service";
import Cookies from "js-cookie";
import Alert from "../../components/alert/Alert";
import { FaRegTrashAlt } from "react-icons/fa";
import ConfirmDeleteModal from "../../components/confirmDeleteModal/ConfirmDeleteModal";
import { useNavigate } from "react-router-dom";

export default function ProfileSettings() {
  const [name, setName] = useState("");
  const [newEmail, setNewEmail] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [isEditing, setIsEditing] = useState(null);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertType, setAlertType] = useState("");
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const navigate = useNavigate();

  const userService = new UserService();
  const myEmail = Cookies.get("email");
  const myPassword = Cookies.get("password");
  const [email, setEmail] = useState(myEmail);
  const [password, setPassword] = useState(myPassword);

  const handleEdit = (field) => {
    setIsEditing(field);
  };

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const user = await userService.getUserMe(myEmail, myPassword);
        setName(user.name);
      } catch (error) {
        setAlertMessage("Fehler beim Laden der Benutzerdaten.");
        setAlertType("error");
      }
    };
    fetchUserData();
  }, [myEmail, myPassword, userService]);

  const handleSave = () => {
    setIsEditing(null);
  };

  const handleKeyDown = async (event, field) => {
    if (event.key === "Enter") {
      try {
        if (field === "email") {
          await userService.userPatchUpdateEmail(
            myEmail,
            myPassword,
            event.target.value
          );
          Cookies.set("email", event.target.value);
          setNewEmail(event.target.value);
          setAlertMessage("Email erfolgreich geändert.");
          setAlertType("success");
        } else if (field === "password") {
          await userService.userPatchUpdatePassword(
            email,
            myPassword,
            event.target.value
          );
          Cookies.set("password", event.target.value);
          setNewPassword(event.target.value);
          setAlertMessage("Passwort erfolgreich geändert.");
          setAlertType("success");
        }
        handleSave();
      } catch (error) {
        setAlertMessage(
          error.message ||
            `Fehler beim Ändern der ${
              field === "email" ? "E-Mail Adresse" : "Passwort"
            }`
        );
        setAlertType("error");
      }
    }
  };

  const handleDeleteProfile = async () => {
    setAlertMessage("Du hast dein Profil erfolgreich gelöscht.");
    setAlertType("success");

    setTimeout(async () => {
      try {
        await userService.deleteUserProfile(email, password);
        Cookies.remove("email");
        Cookies.remove("password");

        setTimeout(() => {
          navigate("/");
        }, 2000);
      } catch (error) {
        setAlertMessage(error.message || "Fehler beim Löschen des Profils.");
        setAlertType("error");
      }
    }, 500);
  };

  return (
    <div className="profile-settings">
      <div>
        <h2>Profil Einstellungen</h2>
      </div>
      <div className="profile-settings-container">
        <div className="profileField">
          <label>Dein Name</label>
          <div>
            <InputField label={name} disabled={true} />
          </div>
        </div>
        <div className="profileField">
          <label>Email Adresse</label>
          {isEditing === "email" ? (
            <InputField
              type="text"
              value={newEmail || email}
              onChange={(e) => setEmail(e.target.value)}
              onKeyDown={(e) => handleKeyDown(e, "email")}
            />
          ) : (
            <div>
              <InputField label={email} disabled={true} />
              <button onClick={() => handleEdit("email")}>ändern</button>
            </div>
          )}
        </div>
        <div className="profileField">
          <label>Passwort</label>
          {isEditing === "password" ? (
            <InputField
              value={newPassword || password}
              onChange={(e) => setPassword(e.target.value)}
              onKeyDown={(e) => handleKeyDown(e, "password")}
            />
          ) : (
            <div>
              <InputField type="password" value={myPassword} disabled={true} />
              <button onClick={() => handleEdit("password")}>ändern</button>
            </div>
          )}
        </div>
      </div>
      <div className="delete-profile-container">
        <button onClick={() => setShowDeleteModal(true)}>
          <div>
            <FaRegTrashAlt style={{ color: "#f04" }} />
          </div>

          <p>Profil löschen</p>
        </button>
      </div>

      <ConfirmDeleteModal
        show={showDeleteModal}
        onClose={() => setShowDeleteModal(false)}
        onConfirm={handleDeleteProfile}
      />

      <Alert message={alertMessage} type={alertType} />
    </div>
  );
}
