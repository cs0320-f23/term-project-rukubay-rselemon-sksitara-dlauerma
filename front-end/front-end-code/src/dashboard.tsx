import React, { useEffect } from "react";
import Chart from "chart.js/auto";
import "./styles/index.css";

const App = () => {
  useEffect(() => {
    const canvas = document.getElementById(
      "myChart"
    ) as HTMLCanvasElement | null;

    if (canvas) {
      const ctx = canvas.getContext("2d");
      if (ctx) {
        new Chart(ctx, {
          type: "bar",
          data: {
            labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
            datasets: [
              {
                label: "# of Votes",
                data: [12, 19, 3, 5, 2, 3],
                borderWidth: 1,
              },
            ],
          },
          options: {
            scales: {
              y: {
                beginAtZero: true,
              },
            },
          },
        });
      }
    }
  }, []); // Empty dependency array to run the effect only once

  return (
    <div>
      <canvas id="myChart"></canvas>
    </div>
  );
};

export default App;
