import React from "react";
import Chart from "chart.js/auto";
import "./styles/index.css";

export default function ListenChart() {
  // all the shared stated properties that we will need for the REPL
  const canvas = document.getElementById("myChart") as HTMLCanvasElement | null;
  const mockMinutes = [50, 324, 78, 56, 9, 17, 48];

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
              data: mockMinutes,
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
