import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day10 {
	/* Part1 */
	static final int ROWS = 20;
	static final int COLS = 20;
	
	public record Coord (int x, int y) {}
	
	private char startingPointPipe(char[][] map, Coord orig) {
		var upChar = 'X';
		if(orig.y-1 >= 0) {
			upChar = map[orig.y-1][orig.x];
		}
		var downChar = 'X';
		if(orig.y+1 < ROWS) {
			downChar = map[orig.y+1][orig.x];
		}
		
		var rightChar = 'X';
		if(orig.x+1 < COLS) {
			rightChar = map[orig.y][orig.x+1];
		}
		
		var leftChar = 'X';
		if(orig.x-1 >= 0) {
			leftChar = map[orig.y][orig.x-1];
		}
		
		var currChar = 'S';
		
		if(upChar == '|' || upChar == '7' || upChar == 'F') {
			if(downChar == '|' || downChar == 'L' || downChar == 'J') {
				currChar = '|';
			}
			if(rightChar == '-') {
				currChar = 'L';
			}
			if(leftChar == '-') {
				currChar = 'J';
			}
		}
		
		if(leftChar == '-' || leftChar == 'L' || leftChar == 'F') {
			if(rightChar == '-' || rightChar == '7' || rightChar == 'J') {
				currChar = '-';
			}
			if(downChar == '|' || downChar == 'L') {
				currChar = '7';
			}
		}
		if(downChar == '|' || leftChar == 'L' || leftChar == 'J') {
			if(rightChar == '-' || rightChar == 'J' || rightChar == '7') {
				currChar = 'F';
			}	
		}
		
		return currChar;
	}
	
	private boolean isValidMove(char[][] map, Coord orig, Coord neighbour) {
		var origChar = map[orig.y][orig.x];
		var destChar = map[neighbour.y][neighbour.x];
		
		if(destChar == '.') {
			return false;
		}
			
		if(origChar == '|') {
			var isUp = orig.x == neighbour.x && orig.y > neighbour.y;
			var isDown = orig.x == neighbour.x && orig.y < neighbour.y;
			if(isUp && (destChar == '7' || destChar == 'F' || destChar == '|')) {
				return true;
			}
			if(isDown && (destChar == 'L' || destChar == 'J' || destChar == '|')) {
				return true;
			}
		}
		if(origChar == '-') {
			var isLeft = orig.y == neighbour.y && orig.x > neighbour.x;
			var isRight = orig.y == neighbour.y && orig.x < neighbour.x;
			if(isLeft && (destChar == 'L' || destChar == 'F' || destChar == '-')) {
				return true;
			}
			if(isRight && (destChar == '7' || destChar == 'J' || destChar == '-')) {
				return true;
			}
		}
		if(origChar == 'L') {
			var isUp = orig.x == neighbour.x && orig.y > neighbour.y;
			var isRight = orig.y == neighbour.y && orig.x < neighbour.x;
			if(isUp && (destChar == '|' || destChar == 'F' || destChar == '7')) {
				return true;
			}
			if(isRight && (destChar == '-' || destChar == 'J' ||  destChar == '7')) {
				return true;
			}
		}
		if(origChar == 'J') {
			var isUp = orig.x == neighbour.x && orig.y > neighbour.y;
			var isLeft = orig.y == neighbour.y && orig.x > neighbour.x;
			if(isUp && (destChar == '|' || destChar == 'F' || destChar == '7')) {
				return true;
			}
			if(isLeft && (destChar == '-' || destChar == 'F' ||  destChar == 'L')) {
				return true;
			}
		}
		if(origChar == '7') {
			var isLeft = orig.y == neighbour.y && orig.x > neighbour.x;
			var isDown = orig.x == neighbour.x && orig.y < neighbour.y;
			if(isLeft && (destChar == '-' || destChar == 'L' ||  destChar == 'F')) {
				return true;
			}
			if(isDown && (destChar == 'L' || destChar == 'J' || destChar == '|')) {
				return true;
			}
			
		}
		if(origChar == 'F') {
			var isRight = orig.y == neighbour.y && orig.x < neighbour.x;
			var isDown = orig.x == neighbour.x && orig.y < neighbour.y;
			if(isRight && (destChar == '-' || destChar == 'J' ||  destChar == '7')) {
				return true;
			}
			if(isDown && (destChar == 'L' || destChar == 'J' || destChar == '|')) {
				return true;
			}
		}
		return false;
	}
	
	public long calculateDistances(char[][] map, Coord start) {
		long[][] distanceMatrix = new long[map.length][map.length];
		for (long[] row: distanceMatrix) {
		    Arrays.fill(row, -1);
		}
	
		var queueCoords = new LinkedList<Coord>();
		
		map[start.y][start.x] = startingPointPipe(map, start);
		
		queueCoords.add(start);
		distanceMatrix[start.y][start.x] = 0;
		long maxDistance = -1;
				
	    while(!queueCoords.isEmpty()) {
	    	var nextToProcess = queueCoords.pop();
	    	var neighbours = new ArrayList<Coord>();
	    	var n = '.';
	    	if(nextToProcess.y-1 >= 0) {
	    		n = map[nextToProcess.y-1][nextToProcess.x];
	    		if(n != '.') {
	    			var neighbourCoord = new Coord(nextToProcess.x, nextToProcess.y-1);
	    			if(isValidMove(map, nextToProcess, neighbourCoord) &&
	    					distanceMatrix[nextToProcess.y-1][nextToProcess.x] == -1) {	
	    				neighbours.add(neighbourCoord);
	    				var newDistance = distanceMatrix[nextToProcess.y][nextToProcess.x] + 1;
	    				distanceMatrix[nextToProcess.y-1][nextToProcess.x] = newDistance;

	    				if(newDistance > maxDistance) {
	    					maxDistance = newDistance;
	    				}
	    				queueCoords.add(neighbourCoord);
	    			}
	    		}
	    	}
	    	if(nextToProcess.x-1 >= 0) {
	    		n = map[nextToProcess.y][nextToProcess.x-1];
	    		if(n != '.') {
	    			var neighbourCoord = new Coord(nextToProcess.x-1,nextToProcess.y);
	    			if(isValidMove(map, nextToProcess, neighbourCoord) &&
	    					distanceMatrix[nextToProcess.y][nextToProcess.x-1] == -1) {		
	    				neighbours.add(neighbourCoord);
	    				var newDistance = distanceMatrix[nextToProcess.y][nextToProcess.x] + 1;
	    				distanceMatrix[nextToProcess.y][nextToProcess.x-1] = newDistance;

	    				if(newDistance > maxDistance) {
	    					maxDistance = newDistance;
	    				}
	    				queueCoords.add(neighbourCoord);
	    			}
	    		}
	    	}
	    	if(nextToProcess.x+1 < COLS) {
	    		n = map[nextToProcess.y][nextToProcess.x+1];
	    		if(n != '.') {
	    			var neighbourCoord = new Coord(nextToProcess.x+1,nextToProcess.y);
	    			if(isValidMove(map, nextToProcess, neighbourCoord) && 
	    					distanceMatrix[nextToProcess.y][nextToProcess.x+1] == -1) {	
	    				neighbours.add(neighbourCoord);
	    				var newDistance = distanceMatrix[nextToProcess.y][nextToProcess.x] + 1;
	    				distanceMatrix[nextToProcess.y][nextToProcess.x+1] = newDistance;

	    				if(newDistance > maxDistance) {
	    					maxDistance = newDistance;
	    				}
	    				queueCoords.add(neighbourCoord);
	    			}
	    		}
	    	}
	    	if(nextToProcess.y+1 < ROWS) {
	    		n = map[nextToProcess.y+1][nextToProcess.x];
	    		if(n != '.') {
	    			var neighbourCoord = new Coord(nextToProcess.x,nextToProcess.y+1);
	    			if(isValidMove(map, nextToProcess, neighbourCoord) &&
	    					distanceMatrix[nextToProcess.y+1][nextToProcess.x] == -1) {
	    				neighbours.add(neighbourCoord);
	    				var newDistance = distanceMatrix[nextToProcess.y][nextToProcess.x] + 1;
	    				distanceMatrix[nextToProcess.y+1][nextToProcess.x] = newDistance;

	    				if(newDistance > maxDistance) {
	    					maxDistance = newDistance;
	    				}
	    				queueCoords.add(neighbourCoord);
	    			}
	    		}
	    	}
	    }
	    return maxDistance;
	}
		
	public static void main(String[] args) throws IOException {
		Day10 day10 = new Day10();
		FileReader fileReader = new FileReader("input10.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		long res = 0;
		/* part 1*/char[][] map = new char[COLS][ROWS];
		Coord start = null;
		int i = 0;
		
		try {
			while ((line = bufferedReader.readLine()) != null) {
				var l = line.toCharArray();
				map[i] = l;
				if(line.contains("S")) {
					var c = line.indexOf("S");
					start = new Coord(c,i);
				}
				i++;
			}
			/*Part 1*/res = day10.calculateDistances(map, start);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        bufferedReader.close();
        System.out.println(res);
	}

}
