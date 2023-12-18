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
      <h2>Your Top 10 generes</h2>
      <select value={selectedOption} onChange={handleSelectChange}>
        <option value="">Select an option</option>
        <option value="option1">*Pop*</option>
        <option value="option2">*Rock*</option>
        <option value="option3">*Rap*</option>
      </select>

      <p>Selected Option: {selectedOption}</p>
    </div>
  );
}

export default GenreDropdown;
