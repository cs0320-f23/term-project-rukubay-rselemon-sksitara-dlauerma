//import React from "react";
import Chart from "chart.js/auto";
import "./styles/index.css";

export default function ListenChart() {
  // all the shared stated properties that we will need for the REPL
  const canvas = document.getElementById("myChart") as HTMLCanvasElement | null;

  if (canvas) {
    const ctx = canvas.getContext("2d");
    if (ctx) {
      new Chart(ctx, {
        type: "bar",
        data: {
          labels: [
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
          ],
          datasets: [
            {
              label: "# of Votes",
              data: [12, 19, 3, 5, 2, 3, 9],
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

  return (
    <div className="chart">
      <canvas id="myChart"></canvas>
    </div>
  );
}
