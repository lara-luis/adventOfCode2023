import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 {
	public record Pair (String left, String right) {}
	
	/* Part1 */
	public int countStepsBetweenPoints(String[] instructions, Map<String, Pair> map, String origin) {
		var steps = 0;
		var foundDest = false;
		var instructionsIndex = 0;
		var currPlace = origin;
		
		while(!foundDest) {
			var instruction = instructions[instructionsIndex];
			if(instructionsIndex == instructions.length-1) {
				instructionsIndex = 0;
			}else {
				instructionsIndex++;
			}
			
			var dest = map.get(currPlace);
			if(instruction.equals("L")) {
				currPlace = dest.left;
			}else {
				currPlace = dest.right;
			}
			steps++;
			
			if(currPlace.equals("ZZZ")) {
				break;
			}
		}
		
		return steps;
	}
	
	/*Part 2*/
	public Long[] countStepsBetweenPoints(String[] instructions, Map<String, Pair> map, List<String> origins) {
		long steps = 0;
		var foundDest = false;
		var instructionsIndex = 0;
		var currPlaces = new HashMap<String, String>();
		var stepsNeeded = new ArrayList<Long>();
		
		for(var o : origins) {
			currPlaces.put(o, o);
		}
		
		while(!foundDest) {
			var instruction = instructions[instructionsIndex];
			if(instructionsIndex == instructions.length-1) {
				instructionsIndex = 0;
			}
			else {
				instructionsIndex++;
			}
			
			steps++;
			
			var newCurrPlaces = new HashMap<String, String>();
			for(var p : currPlaces.entrySet()) {
				var dest = map.get(p.getValue());
				var currDest = "";
				if(instruction.equals("L")) {
					currDest = dest.left;
				} else {
					currDest = dest.right;
				}
				
				if(currDest.endsWith("Z")) {
					stepsNeeded.add(steps);
				} else {
					newCurrPlaces.put(p.getKey(),currDest);
				}
			}	
			
			currPlaces = newCurrPlaces;
				
			if(stepsNeeded.size() == origins.size()) {
				foundDest = true;
				break;
			}
		}
		
		Long[] array = new Long[stepsNeeded.size()];
		stepsNeeded.toArray(array);
		return array;
	}
	
	// methods from: https://stackoverflow.com/questions/4201860/how-to-find-gcd-lcm-on-a-set-of-numbers
	private long gcd(long a, long b)
	{
	    while (b > 0)
	    {
	        long temp = b;
	        b = a % b; // % is remainder
	        a = temp;
	    }
	    return a;
	}

	private long gcd(long[] input)
	{
	    long result = input[0];
	    for(int i = 1; i < input.length; i++) result = gcd(result, input[i]);
	    return result;
	}
	
	private long lcm(Long a, Long b)
	{
	    return a * (b / gcd(a, b));
	}

	public long lcm(Long[] input)
	{
	    long result = input[0];
	    for(int i = 1; i < input.length; i++) result = lcm(result, input[i]);
	    return result;
	}
	
	public static void main(String[] args) throws IOException {
		Day8 day8 = new Day8();
		FileReader fileReader = new FileReader("input8.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		String[] instructions = null;
		long res = 0;
		Map<String, Pair> map = new HashMap<String, Pair>();
		boolean firstLine = true;
		List<String> startingPoints = new ArrayList<String>();
		try {
			while ((line = bufferedReader.readLine()) != null) {
				if(firstLine) {
					instructions = line.split("");
					firstLine = false;
				} else {
					if(!line.trim().isEmpty()) {
						var tmp = line.split(" = ");
						var origin = tmp[0];
						if(origin.endsWith("A")) {
							startingPoints.add(origin);
						}
						var dest = tmp[1].split(", ");
						var destA = dest[0].substring(1);
						var destB = dest[1].substring(0, 3);
						map.put(origin, new Pair(destA, destB));
					}
				}
			}
			///*Part 1*/ res = day8.countStepsBetweenPoints(instructions, map, "AAA");
			
			/*Part 2*/ 
			var stepsArray = day8.countStepsBetweenPoints(instructions, map, startingPoints);
			System.out.println(day8.lcm(stepsArray));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        bufferedReader.close();
	}

}
