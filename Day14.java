import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day14 {
	/* Part 1 */
	public List<String> transposePuzzle(List<String> puzzle){
		List<String> newPuzzle = new ArrayList<String>();
		
		for(int j = 0 ; j < puzzle.get(0).length(); j++) {
			var newLine = "";
			for(int i = 0 ; i < puzzle.size(); i++) {
				var puzzleRow = puzzle.get(i).split("");
				var c = puzzleRow[j];
				newLine += c;
			}
			StringBuilder input1 = new StringBuilder();
	        input1.append(newLine);
	        input1.reverse();
			newPuzzle.add(input1.toString());
		}
		return newPuzzle;
	}
	
	public int calculateLoad(List<String> map) {
		int result = 0;
		var newMap = new ArrayList<String>();
		
		for(var line : map) {
			var currLine = line;
			for(int i = line.length()-1; i >= 0; i--) {
				if(line.charAt(i) == 'O') {
					var nearestRoundRock = currLine.indexOf('O', i+1);
					var nearestSquareRock = currLine.indexOf('#', i+1);
					var stopPos = -1;
					if(nearestRoundRock == -1 && nearestSquareRock == -1) {
						stopPos = line.length(); 
					} else if(nearestRoundRock == -1) {
						stopPos = nearestSquareRock;
					} else if(nearestSquareRock == -1) {
						stopPos = nearestRoundRock;
					} else {
						stopPos = Math.min(nearestRoundRock, nearestSquareRock);	
					}	
					
					if(stopPos == i+1) {
						// doesn't move
						
					} else {
						if(stopPos > -1) {
							StringBuilder buildLine = new StringBuilder(currLine);
							buildLine.setCharAt(stopPos-1, 'O');
							buildLine.setCharAt(i, '.');
							currLine = buildLine.toString();
							
						}
					}
				}
			}
			newMap.add(currLine);
		}
		
		var lineNumber = 1;
		for(var j = 0; j < newMap.get(0).length(); j++) {
			var count = 0;
			for(var l: newMap) {	
				var tmp = l.split("");
				if(tmp[j].equals("O")) {
					count++;
				}
			}
			result += lineNumber*count;
			lineNumber++;
		}
		return result;
	}
	
	/*Part 2*/
	public List<String> calculateNewMap(List<String> map) {
		var newMap = new ArrayList<String>();
		
		for(var line : map) {
			var currLine = line;
			for(int i = line.length()-1; i >= 0; i--) {
				if(line.charAt(i) == 'O') {
					var nearestRoundRock = currLine.indexOf('O', i+1);
					var nearestSquareRock = currLine.indexOf('#', i+1);
					var stopPos = -1;
					if(nearestRoundRock == -1 && nearestSquareRock == -1) {
						stopPos = line.length(); 
					} else if(nearestRoundRock == -1) {
						stopPos = nearestSquareRock;
					} else if(nearestSquareRock == -1) {
						stopPos = nearestRoundRock;
					} else {
						stopPos = Math.min(nearestRoundRock, nearestSquareRock);	
					}	
					
					if(stopPos == i+1) {
						// doesn't move
						
					} else {
						if(stopPos > -1) {
							StringBuilder buildLine = new StringBuilder(currLine);
							buildLine.setCharAt(stopPos-1, 'O');
							buildLine.setCharAt(i, '.');
							currLine = buildLine.toString();
							
						}
					}
				}
			}
			newMap.add(currLine);
		}
		return newMap;
	}
	
	public int calculateSums(List<String> newMap) {
		var result = 0;
		var lineNumber = 1;
		for(var j = 0; j < newMap.get(0).length(); j++) {
			var count = 0;
			for(var l: newMap) {	
				var tmp = l.split("");
				if(tmp[j].equals("O")) {
					count++;
				}
			}
			result += lineNumber*count;
			lineNumber++;
		}
		return result;
	}
			
	public static void main(String[] args) throws IOException {
		Day14 day14 = new Day14();
		FileReader fileReader = new FileReader("input14.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		long res = 0;
		var map = new ArrayList<String>();
		try {
			while ((line = bufferedReader.readLine()) != null) {
				map.add(line);
			}
			///*Part 1*/ var switchedMap = day14.transposePuzzle(map); // north
			//res = day14.calculateLoad(switchedMap);
			
			/*Part 2*/
			List<String> switchedMap = map;
			for(int i = 1; i <= 1000; i++) {
				switchedMap = day14.transposePuzzle(switchedMap); // north
				switchedMap = day14.calculateNewMap(switchedMap);
				res = day14.calculateSums(switchedMap);
				
				switchedMap = day14.transposePuzzle(switchedMap); // west
				switchedMap = day14.calculateNewMap(switchedMap);
				switchedMap = day14.transposePuzzle(switchedMap);
				switchedMap = day14.transposePuzzle(switchedMap); 
				switchedMap = day14.transposePuzzle(switchedMap);
				
				switchedMap = day14.transposePuzzle(switchedMap); // south
				switchedMap = day14.transposePuzzle(switchedMap); 
				switchedMap = day14.calculateNewMap(switchedMap);	
				switchedMap = day14.transposePuzzle(switchedMap);
				switchedMap = day14.transposePuzzle(switchedMap);
				
				switchedMap = day14.transposePuzzle(switchedMap); // west
				switchedMap = day14.transposePuzzle(switchedMap);
				switchedMap = day14.transposePuzzle(switchedMap);
				switchedMap = day14.calculateNewMap(switchedMap);
				switchedMap = day14.transposePuzzle(switchedMap);
				res = day14.calculateSums(switchedMap);
				
				switchedMap = day14.transposePuzzle(switchedMap);
				switchedMap = day14.transposePuzzle(switchedMap);
				switchedMap = day14.transposePuzzle(switchedMap);
			}
			
			// found the pattern, divided 1000000000 by that pattern interval
			// got the division remaining
			// summed the pattern interval to the remaining over and over again
			// until entering the pattern start numbers
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        bufferedReader.close();
        System.out.println(res);
	}
}