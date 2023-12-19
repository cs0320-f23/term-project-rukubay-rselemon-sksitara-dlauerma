import React from "react";
import GenreDropdown from "./dropdown.tsx";
import { FaMusic, FaUser, FaPlay, FaHeadphones } from "react-icons/fa";
import { useSearchParams, useNavigate } from "react-router-dom";
import { useEffect } from "react";
import { Dispatch, ReactElement, SetStateAction, useState } from "react";
import { useLocation } from "react-router-dom";

function Dashboard() {
  const [searchParams, setSearchParams] = useSearchParams();

  //var authSuccess = true;
  const location = useLocation();
  const { email } = location.state || {};
  const [topArtist, setTopArtist] = useState<string>("");
  const [topSong, setTopSong] = useState<string>("");
  const [topGenre, setTopGenre] = useState<string>("");

  async function getCode() {
    const userCode = searchParams.get("code");
    await fetch(
      "http://localhost:3232/api/get-user-code?code=" + userCode
    ).then((result) => {
      result.json();
    });
    // await fetch("http://localhost:3232/api/make-user?username=siddu&password=1")
    //   .then((result) => {
    //     result.json();
    //   })
    //   .then((r1) => console.log(r1));
    await fetch("http://localhost:3232/api/top-artists")
      .then((r1) => r1.json())
      .then((r2) => {
        console.log(r2);
        setTopArtist(r2["artists"][0].name);
      });
    await fetch("http://localhost:3232/api/top-songs")
      .then((r1) => r1.json())
      .then((r2) => {
        console.log(r2);
        setTopSong(r2["songs"][0].name);
      });
    await fetch("http://localhost:3232/api/top-genres")
      .then((r1) => r1.json())
      .then((r2) => {
        console.log(r2);
        setTopGenre(r2["genres"][0]);
      });
  }
  // async function makeUser() {
  //   await fetch(
  //     //"http://localhost:3232/api/make-user?username=" + email + "&password=1"
  //     "http://localhost:3232/api/make-user?username=sid@gmail.com&password=1"
  //   ).then((result) => result.json());
  //   await fetch("http://localhost:3232/api/top-artists")
  //     .then((r1) => r1.json())
  //     .then((r2) => {
  //       console.log(r2);
  //       setTopArtist(r2["artists"][0]);
  //     });
  // }

  useEffect(() => {
    getCode();
    //makeUser();
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
