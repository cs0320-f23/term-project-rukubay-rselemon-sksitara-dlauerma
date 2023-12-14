import React from "react";
import GenreDropdown from "./dropdown.tsx";

function Dashboard() {
  console.log("got here");
  return (
    <div className="Dashboard" aria-label="Dashboard Page">
      <p className="Dashboard-header" aria-label="Application Header">
        <h1 aria-label="Main Title"> Listen Data </h1>
      </p>
      <GenreDropdown />
      <h2> Top Genre: </h2>
      <h2> Top Artist: </h2>
      <h2> Top Song: </h2>
    </div>
  );
}

export default Dashboard;
