import React from "react";
import GenreDropdown from "./dropdown.tsx";
import { FaMusic, FaUser, FaPlay, FaHeadphones } from "react-icons/fa";
import { useSearchParams, useNavigate } from "react-router-dom";
import { useEffect } from "react";

function Dashboard() {
  const [searchParams, setSearchParams] = useSearchParams();

  //var authSuccess = true;

  async function getCode() {
    const userCode = searchParams.get("code");

    await fetch(
      "http://localhost:3232/api/get-user-code?code=" + userCode
    ).then((result) => {
      result.json();
    });
  }

  useEffect(() => {
    getCode();
  }, []);

  const authSuccess = true;
  if (authSuccess) {
    return (
      <div className="Dashboard" aria-label="Dashboard Page">
        <div className="Dashboard-header" aria-label="Application Header">
          <h1 aria-label="Main Title"> *Listen Data* </h1>
        </div>

        <div className="Dashboard-content">
          <div className="Content-block">
            <h2>
              <FaMusic />
              Top Genre:
            </h2>
            {/* Add content related to top genre */}
          </div>

          <div className="Content-block">
            <h2>
              <FaUser />
              Top Artist:
            </h2>
            {/* Add content related to top artist */}
          </div>

          <div className="Content-block">
            <h2>
              <FaPlay />
              Top Song:
            </h2>
            {/* Add content related to top song */}
          </div>

          <div className="Content-block">
            {/* Additional content blocks as needed */}
          </div>
        </div>

        <div className="GenreDropdown">
          <GenreDropdown />
        </div>
        <div className="Additional-decorations">
          <div className="Musical-note">🎵</div>
          <div className="Musical-note">🎵</div>
          <div className="Musical-note">🎵</div>

          <div className="Headphones-icon">
            <FaHeadphones />
          </div>
        </div>
      </div>
    );
  } else {
    return <div> AUTHORIZATION FAILED </div>;
  }
}

export default Dashboard;
