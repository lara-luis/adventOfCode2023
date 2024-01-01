import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day13 {
	/* Part 1 */
	public boolean isMirror(List<String> firstPart, List<String> secondPart) {
		for(int i = 0; i < Math.min(firstPart.size(),secondPart.size()); i++) {
			var getThisLine = firstPart.get(i);
			var getCorrespondingLine = secondPart.get(i);
			if(!getThisLine.equals(getCorrespondingLine)) {
				return false;
			}
		}
		return true;
	}
	
	public List<String> transposePuzzle(List<String> puzzle){
		List<String> newPuzzle = new ArrayList<String>();
		
		for(int j = 0 ; j < puzzle.get(0).length(); j++) {
			var newLine = "";
			for(int i = 0 ; i < puzzle.size(); i++) {
				var puzzleRow = puzzle.get(i).split("");
				var c = puzzleRow[j];
				newLine += c;
			}
			newPuzzle.add(newLine);
		}
		
		return newPuzzle;
	}
	
	public int calculatePuzzle(List<String> puzzle, boolean isRows) {
		var rows = -1;
		var cols = -1;
		
		for(int i = 1; i < puzzle.size(); i++) {
			var previousLine = puzzle.get(i-1);
			var currLine = puzzle.get(i);
			
			if(previousLine.equals(currLine)) {
				List<String> firstPart = puzzle.subList(0, i);				
				List<String> secondPart = puzzle.subList(i, puzzle.size());
				var copy = new ArrayList<>(firstPart);
				Collections.reverse(copy);
				
				if(isMirror(copy,secondPart)) {
					if(isRows) {
						rows = i;	
					} else {
						cols = i;
					}
					
					break;
				}
			}
		}
		
		if(rows > -1) {
			return rows*100; 
		} else if(cols > -1) {
			return cols;
		}
		return 0;
	}
	
	/* Part 2 */
	public boolean isMirror_2(List<String> firstPart, List<String> secondPart) {
		var diffChars = 0;
		
		for(int i = 0; i < Math.min(firstPart.size(),secondPart.size()); i++) {
			var getThisLine = firstPart.get(i);
			var getCorrespondingLine = secondPart.get(i);
			
			if(!getThisLine.equals(getCorrespondingLine)) {
				var thisArray = getThisLine.split("");
				var thatArray = getCorrespondingLine.split("");
				
				for(int k = 0; k < thisArray.length; k++) {
					if(!thisArray[k].equals(thatArray[k])) {
						diffChars++;
					}
				}
			}
		}
		return diffChars == 1;
	}
	
	public int calculatePuzzle_2(List<String> puzzle, boolean isRows) {
		var rows = -1;
		var cols = -1;
		
		for(int i = 1; i < puzzle.size(); i++) {
			List<String> firstPart = puzzle.subList(0, i);				
			List<String> secondPart = puzzle.subList(i, puzzle.size());
			var copy = new ArrayList<>(firstPart);
			Collections.reverse(copy);

			if(isMirror_2(copy,secondPart)) {				
				if(isRows) {
					rows = i;	
				} else {
					cols = i;
				}

				break;
			}
		}
		
		if(rows > -1) {
			return rows*100; 
		} else if(cols > -1) {
			return cols;
		}
		return 0;
	}
		
	public static void main(String[] args) throws IOException {
		Day13 day13 = new Day13();
		FileReader fileReader = new FileReader("input13.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		long res = 0;
		var currPuzzle = new ArrayList<String>();
		try {
			while ((line = bufferedReader.readLine()) != null) {
				if(line.trim().isEmpty()) {
					/* Part 1*/
					/*res += day13.calculatePuzzle(currPuzzle, true);
					var transposedPuzzle = day13.transposePuzzle(currPuzzle);
					res += day13.calculatePuzzle(transposedPuzzle, false);
					*/
					/* Part 2*/
					var rows = day13.calculatePuzzle_2(currPuzzle, true);
					var transposedPuzzle = day13.transposePuzzle(currPuzzle);
					if(rows == 0) {
						var cols = day13.calculatePuzzle_2(transposedPuzzle, false);	
						res += cols;
					}else {
						res += rows;
					}
					
					currPuzzle = new ArrayList<String>();
				} else {
					currPuzzle.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        bufferedReader.close();
        System.out.println(res);
	}
}
