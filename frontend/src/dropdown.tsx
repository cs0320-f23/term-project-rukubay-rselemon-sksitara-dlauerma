import React, { useState, useEffect } from "react";

const GenreDropdown = (props) => {
  const { topTen, onGenreSelect, artist } = props;
  const [selectedOption, setSelectedOption] = useState("");
  const [topArtist, setTopArtist] = useState("");

  useEffect(() => {
    setTopArtist(artist);
  }, [artist]);

  const handleSelectChange = (event) => {
    const selection = event.target.value;
    setTopArtist(artist);
    setSelectedOption(selection);
    onGenreSelect(selection);
  };

  const options = [<option value="">Select an option</option>];
  for (let i = 0; i < topTen.length; i++) {
    options.push(<option value={topTen[i]}> {topTen[i]} </option>);
  }

  return (
    <div>
      <h2>Your Top genres</h2>
      <select value={selectedOption} onChange={handleSelectChange}>
        {options}
      </select>
      <p>
        Top _{selectedOption}_ Artist: {topArtist}
      </p>
    </div>
  );
};

export default GenreDropdown;
