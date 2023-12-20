import React from "react";
import GenreDropdown from "./dropdown.tsx";
import { FaMusic, FaUser, FaPlay, FaHeadphones, FaPlug } from "react-icons/fa";
import { useSearchParams, useNavigate } from "react-router-dom";
import { useEffect } from "react";
import { useState } from "react";

function Dashboard(props) {
  const [searchParams, setSearchParams] = useSearchParams();

  //var authSuccess = true;
  const [topArtist, setTopArtist] = useState<string>("");
  const [topSong, setTopSong] = useState<string>("");
  const [topGenre, setTopGenre] = useState<string>("");
  const [topMatches, setTopMatches] = useState<string>("");
  const userCode = searchParams.get("code");
  const state = searchParams.get("state");
  const username = state !== null ? decodeURI(state).split("|")[0] : "";
  const password = state !== null ? decodeURI(state).split("|")[1] : "";

  async function getCode() {
    //authenticating
    await fetch(
      "http://localhost:3232/api/get-user-code?code=" + userCode
    ).then((result) => {
      result.json();
    });
    //adding user to database
    await fetch(
      "http://localhost:3232/api/make-user?username=" +
        username +
        "&password=" +
        password
    ).then((result) => result.json());
    //getting and computing statistics
    await fetch("http://localhost:3232/api/top-artists?username=" + username)
      .then((r1) => r1.json())
      .then((r2) => {
        setTopArtist(r2["artists"][0].name);
      });
    await fetch("http://localhost:3232/api/top-songs?username=" + username)
      .then((r1) => r1.json())
      .then((r2) => {
        setTopSong(r2["songs"][0].name);
      });
    await fetch("http://localhost:3232/api/top-genres?username=" + username)
      .then((r1) => r1.json())
      .then((r2) => {
        setTopGenre(r2["genres"][0]);
      });
    await fetch(
      "http://localhost:3232/api/compute-statistics?username=" +
        username +
        "&compare-by=artists" +
        "&time-range=short"
    )
      .then((result) => result.json())
      .then((r) => {
        setTopMatches(r["overlaps"]);
        console.log(topMatches);
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
              Top Genre: {topGenre}
            </h2>
            {/* Add content related to top genre */}
          </div>

          <div className="Content-block">
            <h2>
              <FaUser />
              Top Artist: {topArtist}
            </h2>
            {/* Add content related to top artist */}
          </div>

          <div className="Content-block">
            <h2>
              <FaPlay />
              Top Song: {topSong}
            </h2>
            {/* Add content related to top song */}
          </div>

          <div className="Content-block">
            <h2>
              <FaPlug />
              Top Matches: {topMatches}
            </h2>
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
