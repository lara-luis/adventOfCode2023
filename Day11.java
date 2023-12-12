import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day11 {
	/* Part 1 */
	public record Coord (int x, int y) {}
	
	static final int COLS = 140;
	static final int ROWS = 140;
	
	public int calculateSumOfDistances(List<Coord> galaxies) {
		var sumOfDistances = 0;
		var colsWithoutGalaxiesIndex = new ArrayList<Integer>();
		var rowsWithoutGalaxiesIndex = new ArrayList<Integer>();
		
		for(int i = 0; i < COLS; i++) {
			var foundGalaxyThere = false;
			for(int j = 0; j < galaxies.size(); j++) {
				if(galaxies.get(j).x == i) {
					foundGalaxyThere = true;
					break;
				}
			}
			if(!foundGalaxyThere) {
				colsWithoutGalaxiesIndex.add(i);
			}
		}
		
		for(int i = 0; i < ROWS; i++) {
			var foundGalaxyThere = false;
			for(int j = 0; j < galaxies.size(); j++) {
				if(galaxies.get(j).y == i) {
					foundGalaxyThere = true;
					break;
				}
			}
			if(!foundGalaxyThere) {
				rowsWithoutGalaxiesIndex.add(i);
			}
		}
		
		for(int i = 0; i < galaxies.size(); i++) {
			for(int j = i+1; j < galaxies.size(); j++) {	
				if(i == j) {
					continue;
				}
				
				var firstGal = galaxies.get(i);
				var secondGal = galaxies.get(j);
				var spaceCols = 0;
				
				//count empty cols in the middle
				for(var col : colsWithoutGalaxiesIndex) {
					if((col < firstGal.x && col > secondGal.x) || 
						(col > firstGal.x && col < secondGal.x)) {
						spaceCols +=1;
					}
				}
				
				for(var row : rowsWithoutGalaxiesIndex) {
					if((row < firstGal.y && row > secondGal.y) || 
						(row > firstGal.y && row < secondGal.y)) {
						spaceCols +=1;
					}
				}
				
				var currDistance = Math.abs(firstGal.x-secondGal.x) 
						+ Math.abs(firstGal.y-secondGal.y) + spaceCols;
				sumOfDistances += currDistance;
			}
		}
		return sumOfDistances;
	}
	
	/* Part 2 */
	public long calculateSumOfDistancesExpanded(List<Coord> galaxies, int span) {
		var sumOfDistances = 0;
		var colsWithoutGalaxiesIndex = new ArrayList<Integer>();
		var rowsWithoutGalaxiesIndex = new ArrayList<Integer>();
		
		for(int i = 0; i < COLS; i++) {
			var foundGalaxyThere = false;
			for(int j = 0; j < galaxies.size(); j++) {
				if(galaxies.get(j).x == i) {
					foundGalaxyThere = true;
					break;
				}
			}
			if(!foundGalaxyThere) {
				colsWithoutGalaxiesIndex.add(i);
			}
		}
		
		for(int i = 0; i < ROWS; i++) {
			var foundGalaxyThere = false;
			for(int j = 0; j < galaxies.size(); j++) {
				if(galaxies.get(j).y == i) {
					foundGalaxyThere = true;
					break;
				}
			}
			if(!foundGalaxyThere) {
				rowsWithoutGalaxiesIndex.add(i);
			}
		}
		
		for(int i = 0; i < galaxies.size(); i++) {
			for(int j = i+1; j < galaxies.size(); j++) {	
				if(i == j) {
					continue;
				}
				
				var firstGal = galaxies.get(i);
				var secondGal = galaxies.get(j);
				long spaceCols = 0;
				long spaceRows = 0;
				
				//count empty cols in the middle
				for(var col : colsWithoutGalaxiesIndex) {
					if((col < firstGal.x && col > secondGal.x) || 
						(col > firstGal.x && col < secondGal.x)) {
						spaceCols += span;
					}
				}
				
				for(var row : rowsWithoutGalaxiesIndex) {
					if((row < firstGal.y && row > secondGal.y) || 
						(row > firstGal.y && row < secondGal.y)) {
						spaceRows += span;
					}
				}
				
				var currDistance = + Math.abs(firstGal.x-secondGal.x) 
						+ Math.abs(firstGal.y-secondGal.y) + spaceRows + spaceCols;
				sumOfDistances += currDistance;
			}
		}
		return sumOfDistances;
	}
	
	public static void main(String[] args) throws IOException {
		Day11 day11 = new Day11();
		FileReader fileReader = new FileReader("input11.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		long res = 0;
		int lineIdx = 0;
		List<Coord> galaxies = new ArrayList<Coord>();
		try {
			while ((line = bufferedReader.readLine()) != null) {
				if(line.contains("#")){
					var colIdx = line.indexOf('#');
					while(colIdx >= 0) {
						galaxies.add(new Coord(colIdx, lineIdx));
						colIdx = line.indexOf('#', colIdx+1);
					}
				}
				else {
					
				}
				lineIdx++;
			}
			///*Part 1*/
			//res = day11.calculateSumOfDistances(galaxies);
			
			/*Part 2*/
			var oneSpan = day11.calculateSumOfDistancesExpanded(galaxies, 1);
			var twoSpan = day11.calculateSumOfDistancesExpanded(galaxies, 2);
			res = (twoSpan - oneSpan)*999998 + oneSpan;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        bufferedReader.close();
        System.out.println(res);
	}

}
