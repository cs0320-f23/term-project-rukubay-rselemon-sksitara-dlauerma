import "../styles/main.css";
import {Dispatch, ReactElement, SetStateAction, useState} from "react";
import { ControlledInput } from "./ControlledInput";
import {REPLFunction, REPLFunctionMap} from "./REPLFunction";

//sets input props
interface REPLInputProps {
  history: ReactElement[]
  setHistory: Dispatch<SetStateAction<ReactElement[]>>
  data: string[][] | undefined;
  setData: Dispatch<SetStateAction<string[][] | undefined>>;
  isLoaded: boolean;
  setIsLoaded: Dispatch<SetStateAction<boolean>>;
}

//exports REPLInput for outside use
export function REPLInput(props: REPLInputProps) {
  //declare functions and variables
  const [commandString, setCommandString] = useState<string>("");

  let [isBrief, setMode] = useState<boolean>(true);

  const [filePath, setFilePath] = useState<string>("");

  const [hasHeader, setHasHeader] = useState<string>("");
  let fp = "";
  let col;
  let val = "";

  //handleSubmit is run every time a button is pressed
  function handleSubmit(commandString: string) {
    var inputArray = commandString.split(" ");
    var command = inputArray[0]
    // Deals with switching the mode between brief and verbose
    if (command == "mode") {
      handleMode()
    }
    else if (REPLFunctionMap.has(command)) {
      var commandFunction = REPLFunctionMap.get(command)
      if(commandFunction) {
        var result = commandFunction(inputArray)
        addOutput(result)
      }
      else {
        props.setHistory([...props.history, buildResultTable([["Please enter a valid command"]])])
      }
    }
    else {
      props.setHistory([...props.history, buildResultTable([["Please enter a valid command"]])])
    }
    setCommandString("")
  }

  /**
   * This function switches the mode
   */
  function handleMode() {
    setMode(!isBrief);
    props.setHistory([...props.history, buildResultTableMode([["Mode switched"]])])
  }

  /**
   * This is a helper for buildResultTable
   * @param result
   */
  function buildResultTableHelper(result: string[][]) {
    var tableString = "<table>";
    for (const row of result) {
      let rowString = "<tr>";
      for (const value of row) {
        rowString += "<td>" + value + "</td>";
      }
      rowString += "</tr>";
      tableString += rowString;
    }
    tableString += "</table>";
    return <div dangerouslySetInnerHTML={{ __html: tableString}}/>
  }
  /**
   * This function builds a html table given the fileData or just the output that should be displayed
   * @param result is the 2d string array that will be converted to a html table
   * @returns a html table as a string
   */
  function buildResultTable(result: string[][]) {
    var returnTable = buildResultTableHelper(result)
    if(isBrief) {
      return returnTable
    }
    else {
      return (<div><p>Command: <br></br>{commandString}{" "}</p><p>Output: {returnTable} </p></div>)
    }
  }

  /**
   * Different resultTable built for when mode is switched
   * @param result
   */
  function buildResultTableMode(result: string[][]) {
    var returnTable = buildResultTableHelper(result)
    if(!isBrief) {
      return returnTable
    }
    else {
      return (<div><p>Command: <br></br>{commandString}{" "}</p><p>Output: {returnTable} </p></div>)
    }
  }

  /**
   * Adds the output to the history
   * @param result
   */
  function addOutput(result: Promise<string[][]>) {
    result
    .then(r => buildResultTable(r))
        .then(response => props.setHistory([...props.history, response]))
  }

  /**
   * This function checks if the file is empty or not
   * @returns true if it is empty and false otherwise
   */
  function checkIfFileEmpty() {
    if (!props.data) {
      return true;
    } else {
      if (props.data.length == 0) {
        return true;
      }
      for (const row in props.data) {
        if (row.length > 0) {
          return false;
        }
      }
      return true;
    }
  }

  /**
   * These are command shortcuts for the user to use
   */
  document.addEventListener("keydown", function(event) {
    if(event.metaKey || event.ctrlKey) {
      if(event.key === "b") {
        handleMode();
      }
      else if(event.key === "i") {
        document.getElementById("command-box")!.focus();
      }
      else if(event.key === "Enter") {
        document.getElementById("submit-button")!.focus()
      }
      else if(event.key === "ArrowUp") {
        document.getElementById("Command History")!.focus()
        document.getElementById("Command History")!.scrollBy(0, -10)
      }
      else if(event.key === "ArrowDown") {
        document.getElementById("Command History")!.focus()
        document.getElementById("Command History")!.scrollBy(0, 10)
      }
    }
  });

  /**
   * Helps the user submit by pressing enter
   * @param event
   */
  const enterPress = (event: {key: string}) => {
    if(event.key === "Enter") {
      handleSubmit(commandString)
    }
  }

  return (
    <div className="repl-input">
      <fieldset>
        <legend>Enter a command:</legend>
        <ControlledInput
          value={commandString}
          setValue={setCommandString}
          ariaLabel={"Command input"}
          onKeyPress={enterPress}
        />
      </fieldset>
      <button
          id="submit-button"
          onClick={() => handleSubmit(commandString)}>Submit</button>
      <button onClick={() => handleMode()}>
        {isBrief ? "Brief" : "Verbose"}
      </button>
    </div>
  );
}
