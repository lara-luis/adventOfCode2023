import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day9 {
	/* Part1 */
	public record Pair (int v1, int v2) {}
	
	public int calculateNextValue(String[] historyLine) {
		var intervalValueIsZero = false;
		Stack<Pair> stack = new Stack<Pair>();
		var list = Arrays.asList(historyLine);
		List<Integer> histLine = list.stream()
				  .map(Integer::valueOf)
				  .collect(Collectors.toList());
		
		var currLine = histLine;
		while(!intervalValueIsZero) {
			var curr = -1;
			var next = -1;
			var interval = next - curr;
			var newLine = new ArrayList<Integer>();
			intervalValueIsZero = true;
			for(int i = 1 ; i < currLine.size(); i++) {
				curr = currLine.get(i-1);
				next = currLine.get(i);
				interval = next - curr;
				intervalValueIsZero = intervalValueIsZero && interval == 0;
				newLine.add(interval);
			}
			currLine = newLine;
			stack.add(new Pair(next, interval));
		}
		
		var sum = 0;
		while(!stack.empty()) {
			var curr = stack.pop();
			sum = curr.v1 + sum;
		}
		
		return sum;
	}
	
	/* Part2 */
	public int calculatePreviousValue(String[] historyLine) {
		var intervalValueIsZero = false;
		Stack<Day9.Pair> stack = new Stack<Pair>();
		var list = Arrays.asList(historyLine);
		List<Integer> histLine = list.stream()
				  .map(Integer::valueOf)
				  .collect(Collectors.toList());
		
		var currLine = histLine;
		while(!intervalValueIsZero) {
			var curr = -1;
			var next = -1;
			var interval = next - curr;
			var newLine = new ArrayList<Integer>();
			intervalValueIsZero = true;
			var first = 0;
			var firstInterval = 0;
			for(int i = 1 ; i < currLine.size(); i++) {
				curr = currLine.get(i-1);				
				next = currLine.get(i);
				interval = next - curr;
				intervalValueIsZero = intervalValueIsZero && interval == 0;
				newLine.add(interval);
				
				if(i == 1) {
					first = curr;
					firstInterval = interval;
				}
			}
			currLine = newLine;
			stack.add(new Pair(first, firstInterval));
		}
		
		var sum = 0;
		while(!stack.isEmpty()) {
			var curr = stack.pop();
			sum = curr.v1 - sum;
		}
		
		return sum;
	}
	
	public static void main(String[] args) throws IOException {
		Day9 day9 = new Day9();
		FileReader fileReader = new FileReader("input9.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		long res = 0;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				var historyLine = line.split(" ");
				///* Part 1 */ res += day9.calculateNextValue(historyLine);
				/*Part 2*/res += day9.calculatePreviousValue(historyLine);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        bufferedReader.close();
        System.out.println(res);
	}

}
