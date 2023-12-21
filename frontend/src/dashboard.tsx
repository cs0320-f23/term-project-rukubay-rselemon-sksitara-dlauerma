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
  const [topArtistGenre, setTopArtistGenre] = useState<string>("");
  const [topSong, setTopSong] = useState<string>("");
  const [topGenre, setTopGenre] = useState<string>("");
  const [topTen, setTopTen] = useState<string[]>([]);
  const [topMatches, setTopMatches] = useState<string>("");
  const [artistsData, setArtistsData] = useState([]);
  const [genreToArtistMap, setGenreToArtistMap] = useState(new Map());

  const userCode = searchParams.get("code");
  const state = searchParams.get("state");
  const username = state !== null ? decodeURI(state).split("|")[0] : "";
  const password = state !== null ? decodeURI(state).split("|")[1] : "";

  function sortArtist(artists) {
    var genreToArtist = new Map();
    console.log("artists", artists);
    console.log("topten", topTen);
    //genreToArtist.set("", ["", ""]);
    for (let i = 0; i < 50; i++) {
      var relevantGenres = artists[i].genres;
      for (let j = 0; j < relevantGenres.length; j++) {
        var genre = relevantGenres[j];
        //console.log(genre);
        if (!genreToArtist.has(genre) && topTen.includes(genre)) {
          genreToArtist.set(genre, [artists[i].name, artists[i]["images"]]);
          //console.log(genreToArtist);
          break;
        }
      }
    }
    console.log(genreToArtist);
    setGenreToArtistMap(genreToArtist);
  }

  useEffect(() => {
    console.log("faba", genreToArtistMap);
  }, [genreToArtistMap]);

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
    await fetch("http://localhost:3232/api/top-genres?username=" + username)
      .then((r1) => r1.json())
      .then((r2) => {
        if (r2["result"] == "success") {
          setTopGenre(r2["genres"][0]);
          setTopTen(r2["genres"].slice(0, 10));
        }
      });
    await fetch("http://localhost:3232/api/top-artists?username=" + username)
      .then((r1) => r1.json())
      .then((r2) => {
        if (r2["result"] == "success") {
          setTopArtist(r2["artists"][0].name);
          setArtistsData(r2["artists"]);
          //sortArtist(r2["artists"]);
        }
      });
    await fetch("http://localhost:3232/api/top-songs?username=" + username)
      .then((r1) => r1.json())
      .then((r2) => {
        if (r2["result"] == "success") {
          setTopSong(r2["songs"][0].name);
        }
      });
    await fetch(
      "http://localhost:3232/api/compute-statistics?username=" +
        username +
        "&compare-by=artists" +
        "&time-range=short"
    )
      .then((result) => result.json())
      .then((r) => {
        if (r["result"] == "success") {
          setTopMatches(r["overlaps"][0]);
        }
      });
  }

  useEffect(() => {
    getCode();
  }, []);

  const authSuccess = true;
  if (authSuccess) {
    //console.log("gen", topArtistGenre);
    return (
      <div className="Dashboard" aria-label="Dashboard Page">
        <div className="Dashboard-header" aria-label="Application Header"></div>
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
            //artist="whyamistillhere"
            artist={genreToArtistMap.get(topArtistGenre)}
          />
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
