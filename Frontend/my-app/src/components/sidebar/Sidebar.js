import React, { useEffect } from "react";
import "./Sidebar.css";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { IoHomeOutline } from "react-icons/io5";
import { CgProfile } from "react-icons/cg";
import { GrLogin, GrLogout } from "react-icons/gr";
import { IoLibraryOutline } from "react-icons/io5";
import { MdOutlineFavoriteBorder } from "react-icons/md";
import Cookies from "js-cookie";

import Logo from "../../assets/images/Logo.svg";
import LogoSmall from "../../assets/images/LogoSmall.svg";
import ButtonSVG from "../../assets/images/ButtonSVG.svg";
import ButtonSVGClose from "../../assets/images/ButtonSVGClose.svg";

export default function Sidebar({ showSidebar, setShowSidebar }) {
  const location = useLocation();
  const navigate = useNavigate();
  const isAuthenticated = Cookies.get("email");

  console.log("isAuthenticated", isAuthenticated);

  useEffect(() => {
    const handleResize = () => {
      setShowSidebar(window.innerWidth > 1100);
    };
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, [setShowSidebar]);

  const handleLogout = () => {
    Cookies.remove("email");
    Cookies.remove("password");
    navigate("/login");
  };

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
                {isAuthenticated && (
                  <>
                    <li
                      className={`li-btn ${
                        location.pathname === "/favorites" ? "active" : ""
                      }`}>
                      <div>
                        <Link to="/favorites" className="link-btn">
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
                  </>
                )}
              </ul>
            </div>
            <div className="logout-container">
              <div>
                <ul>
                  <li>
                    <div>
                      {isAuthenticated ? (
                        <button onClick={handleLogout} className="link-btn">
                          <GrLogout />
                          <p>Abmelden</p>
                        </button>
                      ) : (
                        <Link to="/login" className="link-btn">
                          <GrLogin />
                          <p>Anmelden</p>
                        </Link>
                      )}
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
                {isAuthenticated && (
                  <>
                    <li
                      className={`li-btn ${
                        location.pathname === "/favorites" ? "active" : ""
                      }`}>
                      <div>
                        <Link to="/favorites" className="link-btn">
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
                  </>
                )}
              </ul>
            </div>
            <div className="logout-container">
              <div>
                <ul>
                  <li>
                    <div>
                      {isAuthenticated ? (
                        <button onClick={handleLogout} className="link-btn">
                          <GrLogout />
                        </button>
                      ) : (
                        <Link to="/login" className="link-btn">
                          <GrLogin />
                        </Link>
                      )}
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
