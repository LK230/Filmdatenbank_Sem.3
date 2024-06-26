import React from "react";
import "./BackdropCard.css";

export default function BackdropCard({ img, onClick }) {
    return (
        <div className="BackdropCard-container" onClick={onClick}>
            <img src={img} alt=""/>
        </div>
    );
}