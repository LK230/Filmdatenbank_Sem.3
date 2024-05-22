import React, { useEffect } from "react";
import "./Sidebar.css";
import { Link, useLocation } from "react-router-dom";
import { IoHomeOutline } from "react-icons/io5";
import { CgProfile } from "react-icons/cg";
import { GrLogout } from "react-icons/gr";
import { IoLibraryOutline } from "react-icons/io5";
import { MdOutlineFavoriteBorder } from "react-icons/md";

import Logo from "../../assets/images/Logo.svg";
import LogoSmall from "../../assets/images/LogoSmall.svg";
import ButtonSVG from "../../assets/images/ButtonSVG.svg";
import ButtonSVGClose from "../../assets/images/ButtonSVGClose.svg";

export default function Sidebar({ showSidebar, setShowSidebar }) {
  const location = useLocation();

  useEffect(() => {
    const handleResize = () => {
      setShowSidebar(window.innerWidth > 800);
    };
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, [setShowSidebar]);

  return (
    <div className={`Sidebar ${showSidebar ? "open" : "close"}`}>
      {showSidebar ? (
        <div className="sidebar-container">
          <div className="logo">
            <img src={Logo} alt="CineCritique Logo"></img>
          </div>
          <div className="items-container open">
            <div className="pages-container">
              <ul>
                <li
                  className={`li-btn ${
                    location.pathname === "/" ? "active" : ""
                  }`}>
                  <div>
                    <Link to="/" className="link-btn">
                      <IoHomeOutline />
                      <p>Home</p>
                    </Link>
                  </div>
                </li>
                <li
                  className={`li-btn ${
                    location.pathname === "/genres" ? "active" : ""
                  }`}>
                  <div>
                    <Link to="/genres" className="link-btn">
                      <IoLibraryOutline />
                      <p>Genres</p>
                    </Link>
                  </div>
                </li>
                <li
                  className={`li-btn ${
                    location.pathname === "/favourites" ? "active" : ""
                  }`}>
                  <div>
                    <Link to="/favourites" className="link-btn">
                      <MdOutlineFavoriteBorder />
                      <p>Gespeichert</p>
                    </Link>
                  </div>
                </li>
                <li
                  className={`li-btn ${
                    location.pathname === "/profile" ? "active" : ""
                  }`}>
                  <div>
                    <Link to="/profile" className="link-btn">
                      <CgProfile />
                      <p>Profil</p>
                    </Link>
                  </div>
                </li>
              </ul>
            </div>
            <div className="logout-container">
              <div>
                <ul>
                  <li>
                    <div>
                      <Link to="/logOut" className="link-btn">
                        <GrLogout />
                        <p>Abmelden</p>
                      </Link>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div className="sidebar-container">
          <div className="logo">
            <img src={LogoSmall} alt="CineCritique Logo"></img>
          </div>
          <div className="items-container close">
            <div className="pages-container">
              <ul>
                <li
                  className={`li-btn ${
                    location.pathname === "/" ? "active" : ""
                  }`}>
                  <div>
                    <Link to="/" className="link-btn">
                      <IoHomeOutline />
                    </Link>
                  </div>
                </li>
                <li
                  className={`li-btn ${
                    location.pathname === "/genres" ? "active" : ""
                  }`}>
                  <div>
                    <Link to="/genres" className="link-btn">
                      <IoLibraryOutline />
                    </Link>
                  </div>
                </li>
                <li
                  className={`li-btn ${
                    location.pathname === "/favourites" ? "active" : ""
                  }`}>
                  <div>
                    <Link to="/favourites" className="link-btn">
                      <MdOutlineFavoriteBorder />
                    </Link>
                  </div>
                </li>
                <li
                  className={`li-btn ${
                    location.pathname === "/profile" ? "active" : ""
                  }`}>
                  <div>
                    <Link to="/profile" className="link-btn">
                      <CgProfile />
                    </Link>
                  </div>
                </li>
              </ul>
            </div>
            <div className="logout-container">
              <div>
                <ul>
                  <li>
                    <div>
                      <Link to="/logOut" className="link-btn">
                        <GrLogout />
                      </Link>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      )}
      <div className="open-close-btn">
        <button onClick={() => setShowSidebar(!showSidebar)}>
          <img
            src={showSidebar ? ButtonSVG : ButtonSVGClose}
            alt="open-close Button"
          />
        </button>
      </div>
    </div>
  );
}
