import React, { useState } from "react";

const GenreDropdown = (props) => {
  const { topTen, onGenreSelect, artist } = props;
  const [selectedOption, setSelectedOption] = useState("");
  const [topArtist, setTopArtist] = useState("");

  const handleSelectChange = (event) => {
    const selection = event.target.value;
    setSelectedOption(selection);
    onGenreSelect(selection);
    setTopArtist(artist);
  };

  const options = [<option value="">Select an option</option>];
  for (let i = 0; i < 10; i++) {
    options.push(<option value={topTen[i]}> {topTen[i]} </option>);
  }

  return (
    <div>
      <h2>Your Top 10 genres</h2>
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
