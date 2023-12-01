import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day1 {
	/*Part 1*/
	public int ProcessLine_1(String line) {
		char[] chars = line.toCharArray();
		int firstDigit = -1;
		int lastDigit = -1;
		
		for(int i = 0; i<chars.length;i++) {
			if (Character.isDigit(chars[i])) {
				firstDigit = Integer.parseInt(chars[i]+"");
				break;
			}
		}
		
		for(int i = chars.length-1; i >= 0; i--) {
			if (Character.isDigit(chars[i])) {
				lastDigit = Integer.parseInt(chars[i]+"");
				break;
			}
		}
		
		if (firstDigit == -1) {
			return 0;
		}
		
		if(lastDigit == -1) {
			lastDigit = firstDigit;
		}
		
		return Integer.parseInt(firstDigit+""+lastDigit);
	}

	/*Part 2*/
	String[] numsExt = new String[] {
			"zero",
			"one",
			"two",
			"three",
			"four", 
			"five",
			"six",
			"seven", 
			"eight",
			"nine"};
	
	Integer[] nums = new Integer[] {0,1,2,3,4,5,6,7,8,9};
	
	private int GetFirstInt(String line) {
		int earlierIndex = Integer.MAX_VALUE;
		int earlierNumber = -1;
		
		for(int i=0 ; i<numsExt.length;i++) {
			var numExt = numsExt[i];
			var num = nums[i];
			var extFirstNumberIndex = line.indexOf(numExt);
			var firstNumberIndex = line.indexOf(num.toString());

			if(extFirstNumberIndex != -1 && extFirstNumberIndex < earlierIndex) {
				earlierIndex = extFirstNumberIndex;
				earlierNumber = nums[i];
			}
			if(firstNumberIndex != -1 && firstNumberIndex < earlierIndex) {
				earlierIndex = firstNumberIndex;
				earlierNumber = nums[i];
			}
		}
		return earlierNumber;
	}
	
	private int GetLastInt(String line) {
		int laterIndex = -1;
		int laterNumber = -1;
		
		for(int i=numsExt.length-1 ; i>=0;i--) {
			var numExt = numsExt[i];
			var num = nums[i];
			var extFirstNumberIndex = line.lastIndexOf(numExt);
			var firstNumberIndex = line.lastIndexOf(num.toString());

			if(extFirstNumberIndex != -1 && extFirstNumberIndex > laterIndex) {
				laterIndex = extFirstNumberIndex;
				laterNumber = nums[i];
			}
			if(firstNumberIndex != -1 && firstNumberIndex > laterIndex) {
				laterIndex = firstNumberIndex;
				laterNumber = nums[i];
			}
		}
		return laterNumber;
	}
	
	public int ProcessLine_2(String line) {
		int firstDigit = GetFirstInt(line);
		int lastDigit = GetLastInt(line);
				
		if (firstDigit == -1) {
			return 0;
		}
		
		if(lastDigit == -1) {
			lastDigit = firstDigit;
		}
		
		return Integer.parseInt(firstDigit+""+lastDigit);
	}
		
	public static void main(String[] args) throws IOException {
		Day1 day1 = new Day1();
		FileReader fileReader = new FileReader("input1.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        int sum = 0;
        try {
			while ((line = bufferedReader.readLine()) != null) {
				sum += day1.ProcessLine_2(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        bufferedReader.close();
        System.out.println(sum);
	}

}
