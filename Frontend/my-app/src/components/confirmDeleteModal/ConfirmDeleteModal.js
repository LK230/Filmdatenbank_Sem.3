import React from "react";
import "./ConfirmDeleteModal.css";

export default function ConfirmDeleteModal({ show, onClose, onConfirm }) {
  if (!show) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>Bist du dir sicher, dass Du dein Profil löschen möchtest?</h2>
        <div className="modal-buttons">
          <button className="confirm-button" onClick={onConfirm}>
            Bestätigen
          </button>
          <button className="cancel-button" onClick={onClose}>
            Abbrechen
          </button>
        </div>
      </div>
    </div>
  );
}
