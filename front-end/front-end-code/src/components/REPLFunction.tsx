import { Simulate } from "react-dom/test-utils";
import { mockData } from "../../mocked-data/mockedJson";
import input = Simulate.input;

export interface REPLFunction {
  (args: string[]): Promise<string[][]>;
}
let port = 3232;
let fp = "";

/**
 * This map stores the command name to its function
 */
export const REPLFunctionMap = new Map<string, REPLFunction>
REPLFunctionMap.set("load_file", load)
REPLFunctionMap.set("view", view)
REPLFunctionMap.set("search", search)
REPLFunctionMap.set("broadband", broadband)
REPLFunctionMap.set("name", name)

/**
 * This function loads a file
 * @param inputArray
 */
async function load(inputArray: string[]) {
  if (inputArray.length == 2) {
    let fp = inputArray[1];
    return await fetch(
      "http://localhost:" + port + "/loadcsv?filename=" + fp + "&header=false"
    )
      .then((r) => r.json())
      .then((response) => {
        if (!(response["result"] == "success")) {
          //add to history
          return [["File " + fp + " not found"]];
        } else {
          return [["File " + fp + " successfully loaded"]];
        }
      });
  } else if (inputArray.length == 3 && inputArray[3] != "mock") {
    let fp = inputArray[1];
    let headerBoolean = inputArray[2] == "with_header";
    return await fetch(
      "http://localhost:" +
        port +
        "/loadcsv?filename=" +
        fp +
        "&header=" +
        headerBoolean
    )
      .then((r) => r.json())
      .then((response) => {
        if (!(response["result"] == "success")) {
          // add to history
          return [["File " + fp + " not found"]];
        } else {
          return [["Loaded file " + fp + " successfully"]];
        }
      });
  } else if (inputArray.length >= 3 && inputArray[inputArray.length-1] == "mock") {
    let file = inputArray[1];
    let mockdata = mockData.get(file);
    fp = file;
    if (mockdata) {
      return [["Loaded file " + fp + " successfully"]];
    } else {
      return [["File " + fp + " not found"]];
    }
  } else {
    return [["Not a valid load command"]];
  }
}

/**
 * This function views a file
 * @param inputArray
 */
async function view(inputArray: string[]) {
  if (inputArray.length == 1) {
    return await fetch("http://localhost:" + port + "/viewcsv")
      .then((r) => r.json())
      .then((response) => {
        if (!(response["result"] == "success")) {
          return [["Please load a file before viewing it"]];
        } else {
          return response["data"];
        }
      });
  } else if (inputArray.length == 2 && inputArray[1] == "mock") {
    if (fp != "") {
      return mockData.get(fp);
    } else {
      return [["Please load a file before viewing it"]];
    }
  } else {
    return [["Not a valid view command"]];
  }
}

/**
 * This function searches a file
 * @param inputArray
 */
async function search(inputArray: string[]) {
  if (
    inputArray.length > 2 &&
    inputArray[inputArray.length-1] == "mock"
  ) {
    if (fp != "") {
      let mock = mockData.get(fp);
      if (mock) {
        return [mock[1]];
      } else {
        return [["Not a valid filepath"]];
      }
    } else {
      return [["Please load a file before searching it"]];
    }
  } else if (inputArray.length == 2) {
    let val = inputArray[1].replace("_", "%20");
    return await fetch("http://localhost:" + port + "/searchcsv?target=" + val)
      .then((r) => r.json())
      .then((response) => {
        if (!(response["result"] == "success")) {
          return [[response["error"]]];
        } else {
          return response["data"];
        }
      });
  } else if (inputArray.length == 3) {
    let val = inputArray[1].replace("_", "%20");
    let col = inputArray[2];
    return await fetch(
      "http://localhost:" +
        port +
        "/searchcsv?target=" +
        val +
        "&identifier=" +
        col
    )
      .then((r) => r.json())
      .then((response) => {
        if (!(response["result"] == "success")) {
          return [[response["error"]]];
        } else {
          return response["data"];
        }
      });
  } else {
    return [["Not a valid search command"]];
  }
}

async function broadband(inputArray: string[]) {
  if (inputArray.length == 3) {
    let state = inputArray[1];
    let county = inputArray[2];
    return await fetch(
      "http://localhost:" +
        port +
        "/broadband?state=" +
        state +
        "&county=" +
        county
    )
      .then((r) => r.json())
      .then((response) => {
        if (!(response["result"] == "success")) {
          return [[response["error"]]];
        } else {
          return [["% Broadband Access: " + response["broadband"]]];
        }
      });
  } else {
    return [["Not a valid broadband command"]];
  }
}

async function name(inputArray: string[]) {
  let name = inputArray[1]
  return [["Hi " + name]]
}