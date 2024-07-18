import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { IoHomeOutline } from "react-icons/io5";
import { IoLibraryOutline } from "react-icons/io5";
import { CgProfile } from "react-icons/cg";
import { MdOutlineFavoriteBorder } from "react-icons/md";
import { GrLogin, GrLogout } from "react-icons/gr";
import Cookies from "js-cookie";
import "./Header.css";
import Logo from "../../assets/images/LogoSmall.svg"; // Pfad zum Logo-Bild
import { FiMenu, FiX } from "react-icons/fi"; // Burger-Icon und Schließen-Icon

export default function Header() {
  const [menuOpen, setMenuOpen] = useState(false);
  const isAuthenticated = Cookies.get("email");
  const navigate = useNavigate();

  const handleLogout = () => {
    Cookies.remove("email");
    Cookies.remove("password");
    navigate("/login");
  };

  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  return (
    <header className="header">
      <div className="header-logo">
        <Link to="/">
          <img src={Logo} alt="CineCritique Logo" />
        </Link>
      </div>
      <div className="burger-menu-icon" onClick={toggleMenu}>
        {menuOpen ? <FiX size={24} /> : <FiMenu size={24} />}
      </div>
      {menuOpen && (
        <nav className="header-nav">
          <ul>
            <li>
              <Link to="/" onClick={toggleMenu}>
                <IoHomeOutline />
                <span>Home</span>
              </Link>
            </li>
            <li>
              <Link to="/genres" onClick={toggleMenu}>
                <IoLibraryOutline />
                <span>Genres</span>
              </Link>
            </li>
            {isAuthenticated && (
              <>
                <li>
                  <Link to="/favorites" onClick={toggleMenu}>
                    <MdOutlineFavoriteBorder />
                    <span>Gespeichert</span>
                  </Link>
                </li>
                <li>
                  <Link to="/profile" onClick={toggleMenu}>
                    <CgProfile />
                    <span>Profil</span>
                  </Link>
                </li>
                <li>
                  <button
                    onClick={() => {
                      handleLogout();
                      toggleMenu();
                    }}>
                    <GrLogout />
                    <span>Abmelden</span>
                  </button>
                </li>
              </>
            )}
            {!isAuthenticated && (
              <li>
                <Link to="/login" onClick={toggleMenu}>
                  <GrLogin />
                  <span>Anmelden</span>
                </Link>
              </li>
            )}
          </ul>
        </nav>
      )}
    </header>
  );
}
