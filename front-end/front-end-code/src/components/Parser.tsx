export class Parser {
  rosterArray: string[][] = [
    ["Course", "Name", "Age"],
    ["CS32", "Matt", "19"],
    ["CS32", "Frank", "23"],
    ["CS32", "Ben", "21"],
  ];

  peopleArray: string[][] = [
    ["Name", "Height", "Eye", "age"],
    ["Alice", "5.2", "blue", "20"],
    ["Bob", "6.1", "gray", "30"],
    ["Steve", "5.9", "hazel", "40"],
  ];

  map = new Map<string, string[][]>([
    ["roster", this.rosterArray],
    ["people", this.peopleArray],
  ]);
}

export const map = new Parser().map;
