import React, { useState } from "react";

const TimeDropdown = (props) => {
  const { onTimeSelect } = props;
  const [selectedOption, setSelectedOption] = useState("short");

  const handleSelectChange = (event) => {
    const selection = event.target.value;
    setSelectedOption(selection);
    onTimeSelect(selection);
  };

  return (
    <div>
      <h2>Time range: </h2>
      <select value={selectedOption} onChange={handleSelectChange}>
        <option value="short"> Short </option>
        <option value="medium"> Medium </option>
        <option value="long"> Long </option>
      </select>
    </div>
  );
};

export default TimeDropdown;
