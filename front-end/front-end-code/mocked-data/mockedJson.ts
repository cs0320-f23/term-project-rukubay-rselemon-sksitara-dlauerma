/**
 * The mockData represents all the possible filepaths for mocking that we will use in testing
 */
export const mockData = new Map<string, string[][]>()
mockData.set("RealFilePath1", [["Names", "Age"],["Jack", "19"],["Saketh", "19"], ["Obama", "62"]])
mockData.set("RealFilePath2", [["Josiahs", "Ratty", "Andrews"], ["Soloman", "Macmillan", "Smitty-B"], ["Nelson", "OMAC"]])
mockData.set("FilePathOneColumn", [["First"], ["Second"], ["Third"], ["Fourth"], ["Fifth"]])
mockData.set("FilePathEmpty", [])
mockData.set("FilePathOneRow", [["Russell", "Wilson", "Patrick", "Mahomes"]])
mockData.set("RealFilePath1WithoutHeader", [["Jack", "19"],["Saketh", "19"], ["Obama", "62"]])
mockData.set("FilePathWeirdShape", [["One"],["One", "Two"], ["One", "Two", "Three"], ["One", "Two", "Three", "Four"]])

