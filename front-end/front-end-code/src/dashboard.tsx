import React from "react";
import "../styles/App.css";
import ListenChart from "./chart";
import GenreDropdown from "./dropdown";

/**
 * This is the highest level component!
 */
function Dashboard() {
  return (
    <div className="Dashboard" aria-label="Dashboard Page">
      <p className="Dashboard-header" aria-label="Application Header">
        <h1 aria-label="Main Title"> Listen Data </h1>
      </p>
      <ListenChart aria-label="Chart Section" />
      <GenreDropdown aria-label="Genre Dropdown" />
      {/*These should correspond to the genre selected but should be the overall pick by default*/}
      <h2> Top Artist: </h2>
      <h2> Top Song: </h2>
    </div>
  );
}

export default Dashboard;
