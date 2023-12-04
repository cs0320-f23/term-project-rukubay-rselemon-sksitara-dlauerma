import { test, expect } from "@playwright/test";
import {REPLFunctionMap} from "../src/components/REPLFunction";

/**
 * This sets up the url for each test
 */
test.beforeEach(async ({ page }, testInfo) => {
  await page.goto("http://localhost:8000/");
});

test("testing new command", async ({ page }) => {
  await page.getByLabel("Command input").click();
  await page
  .getByLabel("Command input")
  .fill("name Saketh");
  await page.getByRole("button", { name: "Submit" }).click();

  const mock_input2 = `Hi Saketh`;
  await expect(page.getByLabel("Command history")).toContainText(mock_input2);
});