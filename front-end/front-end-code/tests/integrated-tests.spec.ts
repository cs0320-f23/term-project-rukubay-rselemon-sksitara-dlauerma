import { test, expect } from "@playwright/test";

/**
 * This sets up the url for each test
 */
test.beforeEach(async ({ page }, testInfo) => {
  await page.goto("http://localhost:8000/");
});

test("integrated test of ten-star.csv data view", async ({ page }) => {
  await page.getByLabel("Command input").click();
  await page
    .getByLabel("Command input")
    .fill("load_file back-end/data/ten-star.csv with_header");
  await page.getByRole("button", { name: "Submit" }).click();

  const mock_input = `Loaded file back-end/data/ten-star.csv successfully`;
  await expect(page.getByLabel("Command history")).toContainText(mock_input);

  //checking that view works properly
  await page.getByLabel("Command input").fill("view");
  await page.getByRole("button", { name: "Submit" }).click();
  const output1 = `StarIDProperNameXYZ0Sol0001282.434850.004495.36884243.043290.00285-15.241443277.113580.02422223.27753375996 G. Psc7.263881.556430.6869770667`;
  await expect(page.getByLabel("Command history")).toContainText(output1);

  //loading different file
  await page.getByLabel("Command input").click();
  await page
  .getByLabel("Command input")
  .fill("load_file back-end/data/test_stardata1.csv without_header");
  await page.getByRole("button", { name: "Submit" }).click();

  const mock_input2 = `Loaded file back-end/data/test_stardata1.csv successfully`;
  await expect(page.getByLabel("Command history")).toContainText(mock_input2);

  //checking that view works properly
  await page.getByLabel("Command input").fill("view");
  await page.getByRole("button", { name: "Submit" }).click();
  const output2 = `0Sol0001Andreas282.434850.004495.368842Rory43.043290.00285-15.241443Mortimer277.113580.02422223.277534Bailee79.628960.0116452.957945`;
  await expect(page.getByLabel("Command history")).toContainText(output2);
});

test("integrated test of ten-star.csv data search in verbose mode", async ({ page }) => {
  //switching to verbose
  await page.getByLabel("Command input").click();
  await page.getByLabel("Command input").fill("mode");
  await page.getByRole("button", { name: "Submit" }).click();

  const mock_input2 = `Command: mode Output: Mode switched`;
  await expect(page.getByLabel("Command history")).toContainText(mock_input2);

  //loading file
  await page.getByLabel("Command input").click();
  await page
  .getByLabel("Command input")
  .fill("load_file back-end/data/ten-star.csv with_header");
  await page.getByRole("button", { name: "Submit" }).click();

  const mock_input = `Loaded file back-end/data/ten-star.csv successfully`;
  await expect(page.getByLabel("Command history")).toContainText(mock_input);

  //checking that search works with column name
  await page.getByLabel("Command input").fill("search Proxima_Centauri ProperName");
  await page.getByRole("button", { name: "Submit" }).click();

  const output5 = "Output: 70667Proxima Centauri-0.47175-0.36132-1.15037"
  await expect(page.getByLabel("Command history")).toContainText(output5);

  //checking that search works without column name
  await page.getByLabel("Command input").fill("search Rigel_Kentaurus");
  await page.getByRole("button", { name: "Submit" }).click();
  const output3 = "Output: 71454Rigel Kentaurus-0.50359-0.42128-1.176771457Rigel Kentaurus-0.50362-0.42139-1.17665"
  await expect(page.getByLabel("Command history")).toContainText(output3);
});

test("integrated test of broadband data search", async ({ page }) => {
  //running a broadband search
  //checking valid county
  await page.getByLabel("Command input").fill("broadband California Orange");
  await page.getByRole("button", { name: "Submit" }).click();

  const output6 = "% Broadband Access: 93.0"
  await expect(page.getByLabel("Command history")).toContainText(output6);

  //checking invalid county
  await page.getByLabel("Command input").fill("broadband California Yellow");
  await page.getByRole("button", { name: "Submit" }).click();

  const output4 = "Invalid county."
  await expect(page.getByLabel("Command history")).toContainText(output4);
});



