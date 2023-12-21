import React from "react";
import GenreDropdown from "./dropdown.tsx";
import { FaMusic, FaUser, FaPlay, FaHeadphones, FaPlug } from "react-icons/fa";
import { useSearchParams, useNavigate } from "react-router-dom";
import { useEffect } from "react";
import { useState } from "react";
import AlgoDropdown from "./algoSelection.tsx";
import TimeDropdown from "./timeSelection.tsx";

function Dashboard(props) {
  const [searchParams, setSearchParams] = useSearchParams();

  const [topArtist, setTopArtist] = useState<string>("");
  const [topArtistGenre, setTopArtistGenre] = useState<string>("");
  const [topSong, setTopSong] = useState<string>("");
  const [topGenre, setTopGenre] = useState<string>("");
  const [topTen, setTopTen] = useState<string[]>([]);
  const [topMatches, setTopMatches] = useState<string>("");
  const [artistsData, setArtistsData] = useState([]);
  const [genreToArtistMap, setGenreToArtistMap] = useState(new Map());
  const [compareAlgo, setCompareAlgo] = useState<string>("genres");
  const [compareTime, setCompareTime] = useState<string>("short");

  const userCode = searchParams.get("code");
  const state = searchParams.get("state");
  const username = state !== null ? decodeURI(state).split("|")[0] : "";
  const password = state !== null ? decodeURI(state).split("|")[1] : "";

  function sortArtist(artists) {
    var genreToArtist = new Map();
    for (let i = 0; i < artists.length; i++) {
      var relevantGenres = artists[i].genres;
      for (let j = 0; j < relevantGenres.length; j++) {
        var genre = relevantGenres[j];
        if (!genreToArtist.has(genre) && topTen.includes(genre)) {
          genreToArtist.set(genre, [artists[i].name]);
          break;
        }
      }
    }
    setGenreToArtistMap(genreToArtist);
  }

  useEffect(() => {
    if (artistsData.length > 0 && topTen.length > 0) {
      sortArtist(artistsData);
    }
  }, [artistsData, topTen]);

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
    await fetch(
      "http://localhost:3232/api/top-genres?username=" +
        username +
        "&time-range=" +
        compareTime
    )
      .then((r1) => r1.json())
      .then((r2) => {
        if (r2["result"] == "success") {
          setTopGenre(r2["genres"][0]);
          if (r2["genres"].length < 10) {
            setTopTen(r2["genres"].slice(0, r2["genres"].length));
          }
          setTopTen(r2["genres"].slice(0, 10));
        }
      });
    await fetch(
      "http://localhost:3232/api/top-artists?username=" +
        username +
        "&time-range=" +
        compareTime
    )
      .then((r1) => r1.json())
      .then((r2) => {
        if (r2["result"] == "success") {
          setTopArtist(r2["artists"][0].name);
          setArtistsData(r2["artists"]);
        }
      });
    await fetch(
      "http://localhost:3232/api/top-songs?username=" +
        username +
        "&time-range=" +
        compareTime
    )
      .then((r1) => r1.json())
      .then((r2) => {
        if (r2["result"] == "success") {
          setTopSong(r2["songs"][0].name);
        }
      });
    await fetch(
      "http://localhost:3232/api/compute-statistics?username=" +
        username +
        "&compare-by=" +
        compareAlgo +
        "&time-range=" +
        compareTime
    )
      .then((result) => result.json())
      .then((r) => {
        if (r["result"] == "success") {
          setTopMatches(r["overlaps"]);
        }
      });
  }

  useEffect(() => {
    getCode();
  }, [compareAlgo, compareTime]);

  const authSuccess = true;
  if (authSuccess) {
    return (
      <div className="Dashboard" aria-label="Dashboard Page">
        <div className="Dashboard-header" aria-label="Application Header"></div>
        <AlgoDropdown onAlgoSelect={setCompareAlgo} />
        <TimeDropdown onTimeSelect={setCompareTime} />
        <div className="Dashboard-content">
          <div className="Content-block">
            <h2>
              <FaMusic />
              Top Genre: {topGenre}
            </h2>
          </div>

          <div className="Content-block">
            <h2>
              <FaUser />
              Top Artist: {topArtist}
            </h2>
          </div>

          <div className="Content-block">
            <h2>
              <FaPlay />
              Top Song: {topSong}
            </h2>
          </div>

          <div className="Content-block">
            <h2>
              <FaPlug />
              Top Matches: {topMatches}
            </h2>
          </div>
        </div>

        <div className="GenreDropdown">
          <GenreDropdown
            topTen={topTen}
            onGenreSelect={setTopArtistGenre}
            artist={genreToArtistMap.get(topArtistGenre)}
          />
        </div>
        <div className="Additional-decorations">
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
