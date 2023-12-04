import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {
	/*Part 1*/
	static final int NUM_ROWS = 140;
	static final int NUM_COLS = 140;

	static String[][] matrix = new String[NUM_ROWS][NUM_COLS];
	Set<String> symbols = Stream.of("*","#","+","$","%","=","@","/","&","-")
			.collect(Collectors.toCollection(HashSet::new));

	static Boolean[][] checkedPosMatrix = new Boolean[NUM_ROWS][NUM_COLS];	

	private boolean isSymbol(String s) {
		return symbols.contains(s);
	}

	private int getFullNumber(int row, int col) {
		String number = "";

		if(!checkedPosMatrix[row][col]) {
			for(int i = col; i >= 0; i--) {
				if(Character.isDigit(matrix[row][i].toCharArray()[0])){
					number = matrix[row][i] + number;
					checkedPosMatrix[row][i] = true;
				} else {
					break;
				}
			}

			for(int i = col+1; i < NUM_COLS; i++) {
				if(Character.isDigit(matrix[row][i].toCharArray()[0])){
					number = number + matrix[row][i];
					checkedPosMatrix[row][i] = true;
				} else {
					break;
				}
			}
			return Integer.parseInt(number);
		}
		return 0;
	}

	public boolean ValidateRowAndCol(int i, int j) {
		return i > 0 && j > 0 && i < NUM_ROWS && j < NUM_COLS && matrix[i-1][j-1] != null;
	}

	public int sumNumbers() {
		int sum = 0;

		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				if((ValidateRowAndCol(i-1,j-1) && isSymbol(matrix[i-1][j-1])) || 
						(ValidateRowAndCol(i,j-1) && isSymbol(matrix[i][j-1])) || 
						(ValidateRowAndCol(i-1,j) && isSymbol(matrix[i-1][j])) || 
						(ValidateRowAndCol(i+1,j+1) && isSymbol(matrix[i+1][j+1])) || 
						(ValidateRowAndCol(i+1,j) && isSymbol(matrix[i+1][j])) || 
						(ValidateRowAndCol(i-1,j+1) && isSymbol(matrix[i-1][j+1])) ||
						(ValidateRowAndCol(i+1,j-1) && isSymbol(matrix[i+1][j-1])) ||
						(ValidateRowAndCol(i,j+1) && isSymbol(matrix[i][j+1]))) {
					if(Character.isDigit(matrix[i][j].charAt(0))) {
						sum += getFullNumber(i, j);
					}
				}
			}
		}

		return sum;
	}

	/*Part 2*/
	public record Pair (int v1, int v2) {}
	Map<Pair, Set<Integer>> gearsCoords = new HashMap<Pair, Set<Integer>>();

	private int getFullNumber2(int row, int col) {
		String number = "";

		for(int i = col; i >= 0; i--) {
			if(Character.isDigit(matrix[row][i].charAt(0))){
				number = matrix[row][i] + number;
				checkedPosMatrix[row][i] = true;
			} else {
				break;
			}
		}

		for(int i = col+1; i < NUM_COLS; i++) {
			if(Character.isDigit(matrix[row][i].charAt(0))){
				number = number + matrix[row][i];
				checkedPosMatrix[row][i] = true;
			} else {
				break;
			}
		}
		return Integer.parseInt(number);
	}

	public int sumGears() {
		int sum = 0;

		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				if(Character.isDigit(matrix[i][j].charAt(0))) {
					var num = getFullNumber2(i, j);
					var coordi = 0;
					var coordj = 0;

					if(ValidateRowAndCol(i-1,j-1) && matrix[i-1][j-1].equals("*")) {
						coordi = i-1;
						coordj = j-1;
					}
					if(ValidateRowAndCol(i,j-1) && matrix[i][j-1].equals("*")) {
						coordi = i;
						coordj = j-1;
					}
					if(ValidateRowAndCol(i-1,j) && matrix[i-1][j].equals("*")) {
						coordi = i-1;
						coordj = j;
					}
					if(ValidateRowAndCol(i+1,j+1) && matrix[i+1][j+1].equals("*")) {
						coordi = i+1;
						coordj = j+1;
					}
					if(ValidateRowAndCol(i+1,j) && matrix[i+1][j].equals("*")) {
						coordi = i+1;
						coordj = j;
					}
					if(ValidateRowAndCol(i-1,j+1) && matrix[i-1][j+1] != null && matrix[i-1][j+1].equals("*")) {
						coordi = i-1;
						coordj = j+1;
					}
					if(ValidateRowAndCol(i+1,j-1) && matrix[i+1][j-1] != null && matrix[i+1][j-1].equals("*")) {
						coordi = i+1;
						coordj = j-1;
					}
					if(ValidateRowAndCol(i,j+1) && matrix[i][j+1] != null && matrix[i][j+1].equals("*")){
						coordi = i;
						coordj = j+1;
					}

					Pair symbolCoords = new Pair(coordi, coordj);
					var values = gearsCoords.get(symbolCoords);
					if(values == null) {
						values = new HashSet<Integer>();
						values.add(num);
					} else {
						values.add(num);	
					}
					gearsCoords.put(symbolCoords, values);
				}
			}
		}

		for(var c : gearsCoords.entrySet()) {
			if(c.getValue().size() == 2) {
				var set = c.getValue().iterator();
				var product = set.next() * set.next();
				sum += product;
			}
		}

		return sum;
	}

	public static void main(String[] args) throws IOException {
		Day3 day3 = new Day3();
		FileReader fileReader = new FileReader("input3.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		int res = 0;

		for(int i = 0 ; i < NUM_ROWS ; i++) {
			try {
				Arrays.fill(checkedPosMatrix[i], false);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		int i = 0;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				var lineChars = line.split("");
				matrix[i] = lineChars;
				i++;
			}
			/* Part 1*/res = day3.sumNumbers();
			/* Part 2*/res = day3.sumGears(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		bufferedReader.close();
		System.out.println(res);
	}

}
