import React from "react";
import GenreDropdown from "./dropdown.tsx";
import { FaMusic, FaUser, FaPlay, FaHeadphones } from 'react-icons/fa';

function Dashboard() {
  console.log("got here");
  return (
    <div className="Dashboard" aria-label="Dashboard Page">
      <div className="Dashboard-header" aria-label="Application Header">
        <h1 aria-label="Main Title"> Listen Data </h1>
      </div>

      <div className="Dashboard-content">
        <div className="Content-block">
          <h2><FaMusic/>Top Genre:</h2>
          {/* Add content related to top genre */}
        </div>

        <div className="Content-block">
          <h2><FaUser/>Top Artist:</h2>
          {/* Add content related to top artist */}
        </div>

        <div className="Content-block">
          <h2><FaPlay/>Top Song:</h2>
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

        <div className="Headphones-icon"><FaHeadphones /></div>
      </div>
    </div>
  );
}

export default Dashboard;

// click sign up, fetch http://localhost:3232/api/login
// redirect to uri
// check if result is success -> dashboard
// if expires -> say expired
