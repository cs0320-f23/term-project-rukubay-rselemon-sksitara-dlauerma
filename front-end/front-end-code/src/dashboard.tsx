//import React from "react";
import "../styles/App.css";
import ListenChart from "./chart";

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
      <h2> Top Genre: </h2>
      <h2> Top Artist: </h2>
      <h2> Top Song: </h2>
    </div>
  );
}

export default Dashboard;
