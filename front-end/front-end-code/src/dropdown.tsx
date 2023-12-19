import React, { useState } from "react";

function GenreDropdown() {
  // State to manage the selected value
  const [selectedOption, setSelectedOption] = useState("");

  // Handler function for the change event of the select element
  const handleSelectChange = (event) => {
    setSelectedOption(event.target.value);
  };

  // fetch the top genres
  const mockGenreList = [
    "Rap",
    "Hip Hop",
    "Rock",
    "blah",
    "blah",
    "blah",
    "blah",
    "blah",
    "blah",
    "blah",
  ];

  return (
    <div>
      <h2>Your top 10 genres</h2>
      {/* Select element with options */}
      <select value={selectedOption} onChange={handleSelectChange}>
        <option value="">Select an option</option>
        <option value="option1">{mockGenreList[0]}</option>
        <option value="option2">{mockGenreList[1]}</option>
        <option value="option3">{mockGenreList[2]}</option>
        <option value="option4">{mockGenreList[3]}</option>
        <option value="option5">{mockGenreList[4]}</option>
        <option value="option6">{mockGenreList[5]}</option>
        <option value="option7">{mockGenreList[6]}</option>
        <option value="option8">{mockGenreList[7]}</option>
        <option value="option9">{mockGenreList[8]}</option>
        <option value="option10">{mockGenreList[9]}</option>
      </select>

      <p>Genre: {selectedOption}</p>
    </div>
  );
}

export default GenreDropdown;
