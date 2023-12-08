import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {
	/*Part 1*/
	public int getNumberOfWays(long time, long distance) {
		int ways = 0;
		for(int i = 1; i <= time ; i++) {
			var t  = i;
			var d = time - t;
			if(t * d > distance) {
				ways++;
			}
		}
		return ways;
	}
	
	public int getNumberOfWays(String[] times, String[] distances) {
		int res = 1;
		for(int i = 0; i  < times.length; i++) {
			if(!times[i].isEmpty()) {
				res *= getNumberOfWays(Integer.parseInt(times[i].trim()), 
					Integer.parseInt(distances[i].trim()));
			}
		}
		return res;
	}
	
	 public static String[] removeEmpty(String[] origArray) {
	        List<String> newArr = new ArrayList<String>();
	        for (String el : origArray) {
	            if (hasText(el)) {
	                newArr.add(el);
	            }
	        }
	        return newArr.toArray(new String[newArr.size()]);
	    }
	 
	 public static boolean hasText(String str) {
	        return null != str && !"".equals(str.trim());
	    }

	public static void main(String[] args) throws IOException {
		Day6 day6 = new Day6();
		FileReader fileReader = new FileReader("input6.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		int res = 0;
		String[] times = null;
		String[] distances = null;
		
		// part 2
		String time = "";
		String distance = "";
		try {
			while ((line = bufferedReader.readLine()) != null) {
				if(line.contains("Time")) {
					line = line.replace("Time:", "").trim();
					times = line.split(" ");
					times = day6.removeEmpty(times);
					
					for (int i = 0; i< times.length; i++) {
						time += times[i];
					}
				}
				if(line.contains("Distance")) {
					line = line.replace("Distance:", "").trim();
					distances = line.split(" ");
					distances = day6.removeEmpty(distances);
					
					for (int i = 0; i< distances.length; i++) {
						distance += distances[i];
					}
				}
			}
			res = day6.getNumberOfWays(Long.parseLong(time), Long.parseLong(distance));
		} catch (IOException e) {
			e.printStackTrace();
		}
        bufferedReader.close();
        System.out.println(res);
	}

}
