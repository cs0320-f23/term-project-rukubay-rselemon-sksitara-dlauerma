import React, { useState, useEffect } from "react";

const AlgoDropdown = (props) => {
  const { onAlgoSelect } = props;
  const [selectedOption, setSelectedOption] = useState("genres");

  const handleSelectChange = (event) => {
    const selection = event.target.value;
    setSelectedOption(selection);
    onAlgoSelect(selection);
  };

  return (
    <div>
      <h2>Compare by: </h2>
      <select value={selectedOption} onChange={handleSelectChange}>
        <option value="genres"> Genres </option>
        <option value="artists"> Artists </option>
        <option value="songs"> Songs </option>
      </select>
    </div>
  );
};

export default AlgoDropdown;
