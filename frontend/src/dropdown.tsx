import React, { useState } from "react";

function GenreDropdown() {
  // State to manage the selected value
  const [selectedOption, setSelectedOption] = useState("");

  // Handler function for the change event of the select element
  const handleSelectChange = (event) => {
    setSelectedOption(event.target.value);
  };

  return (
    <div>
      <h2>Dropdown</h2>
      <select value={selectedOption} onChange={handleSelectChange}>
        <option value="">Select an option</option>
        <option value="option1">Option 1</option>
        <option value="option2">Option 2</option>
        <option value="option3">Option 3</option>
      </select>

      <p>Selected Option: {selectedOption}</p>
    </div>
  );
}

export default GenreDropdown;
