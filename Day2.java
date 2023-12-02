import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day2 {
	/*Part 1*/
	static final int MAX_BLUES = 14;
	static final int MAX_REDS = 12;
	static final int MAX_GREENS = 13;
	
	public boolean isGamePossible(String line) {
		String[] games = line.split(";");
		boolean isGamePossible = true;
		
		for(String game : games) {
			int blueSum = 0;
			int redSum = 0;
			int greenSum = 0;
			
			String[] sets = game.split(",");
			for(String set : sets) {
				if(set.contains("blue")) {
					String num = set.replace("blue", "");
					blueSum += Integer.parseInt(num.trim());
				}
				if(set.contains("red")) {
					String num = set.replace("red", "");
					redSum += Integer.parseInt(num.trim());
				}
				if(set.contains("green")) {
					String num = set.replace("green", "");
					greenSum += Integer.parseInt(num.trim());
				}
			}
			isGamePossible = isGamePossible && blueSum <= MAX_BLUES && 
					redSum <= MAX_REDS && 
					greenSum <= MAX_GREENS;
		}
		
		return isGamePossible;
	}
	
	/*Part 2*/
	public record Triple (int blue, int red, int green) {}
	
	public Triple minimumCubes(String line) {
		String[] games = line.split(";");
		int maxBlue = -1;
		int maxRed = -1;
		int maxGreen = -1;
		
		for(String game : games) {
			
			String[] sets = game.split(",");
			for(String set : sets) {
				if(set.contains("blue")) {
					String num = set.replace("blue", "");
					maxBlue = Math.max(maxBlue, Integer.parseInt(num.trim()));
				}
				if(set.contains("red")) {
					String num = set.replace("red", "");
					maxRed = Math.max(maxRed, Integer.parseInt(num.trim()));
				}
				if(set.contains("green")) {
					String num = set.replace("green", "");
					maxGreen = Math.max(maxGreen, Integer.parseInt(num.trim()));
				}
			}
		}
		
		return new Triple(maxBlue, maxRed, maxGreen);
	}
		
	public static void main(String[] args) throws IOException {
		Day2 day2 = new Day2();
		FileReader fileReader = new FileReader("input2.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        int res = 0;
        try {
			while ((line = bufferedReader.readLine()) != null) {
				String[] gameSplit = line.split(":");
				/* Part 1*/
				/*int gameNum = Integer.parseInt(gameSplit[0].replace("Game ", ""));
				boolean isGamePossible = day2.IsGamePossible(gameSplit[1]);
				if(isGamePossible) {
					res += gameNum;
				}*/
				/* Part 2 */
				int gameNum = Integer.parseInt(gameSplit[0].replace("Game ", ""));
				var triple = day2.minimumCubes(gameSplit[1]);
				res += (triple.blue * triple.red * triple.green);	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        bufferedReader.close();
        System.out.println(res);
	}

}
