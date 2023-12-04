import {Dispatch, ReactElement, SetStateAction, useState} from 'react';
import '../styles/main.css';
import { REPLHistory } from './REPLHistory';
import { REPLInput } from './REPLInput';

/**
 * This function creates the larger REPL program
 * @constructor
 */
export default function REPL() {
  const [history, setHistory] =
      useState<ReactElement[]>([])
  const [data, setData] =
      useState<string[][] | undefined>();
  const [isLoaded, setIsLoaded] =
      useState<boolean>(false)

  return (
    <div className="repl"> 
      <REPLHistory history ={history}/>
      <hr></hr>
      <REPLInput history={history} setHistory={setHistory} data={data} setData={setData}
                 isLoaded={isLoaded} setIsLoaded={setIsLoaded}/>
    </div>
  );
}
