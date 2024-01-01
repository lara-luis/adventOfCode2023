import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day15 {
	/* Part 1 */
	public int calculateHash(char[] stringArray) {
		var currentValue = 0;
	
		for(int i = 0; i < stringArray.length; i++) {
			var asciiValue = (int)stringArray[i];
			currentValue = (currentValue + asciiValue)*17;
			currentValue = currentValue % 256;
		}
		
		return currentValue;
	}

	public static void main(String[] args) throws IOException {
		Day15 day15 = new Day15();
		FileReader fileReader = new FileReader("input15.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		long res = 0;
		var boxesMap = new HashMap<Integer, List<String>>();
		try {
			while ((line = bufferedReader.readLine()) != null) {
				var sequences = line.split(",");
				
				/* Part 1*/
				//for(var sequence : sequences) {
				//	var charArray = sequence.toCharArray();
				//	res += day15.calculateHash(charArray);	
				//}
				
				/* Part 2 */
				for(var sequence : sequences) {
					var label = "";
					var op = "";
					var focalLength = ""; 
					if(sequence.contains("=")) {
						label = sequence.substring(0, sequence.indexOf("="));
						op = "=";
						focalLength = sequence.substring(sequence.indexOf("=")+1);
					} else {
						label = sequence.substring(0, sequence.indexOf("-"));
						op = "-";
					}
					
					var box = day15.calculateHash(label.toCharArray());
					
					if(op.equals("-")) {
						if(boxesMap.containsKey(box)) {
							var l = boxesMap.get(box);
							
							for(var j = 0; j< l.size(); j++	) {
								var currLen = l.get(j); 
								if(currLen.split(" ")[0].equals(label)) {
									l.remove(currLen);
								}
							}
							
							boxesMap.put(box, l);
						}
					} else {
						if(boxesMap.containsKey(box)) {
							var l = boxesMap.get(box);
							var lensFound = false;
							for(var j = 0; j< l.size(); j++	) {
								var currLen = l.get(j); 
								if(currLen.split(" ")[0].equals(label)) {
									l.set(j, label + " " + focalLength);
									lensFound = true;
								}
							}
							
							if(!lensFound) {
								l.add(label + " " + focalLength);
							}
							boxesMap.put(box, l);
						} else {
							var l = new ArrayList<String>();
							l.add(label + " " + focalLength);
							boxesMap.put(box, l);
						}
					}
					
				}
			}
			
			/* Part 2*/
			for(var e : boxesMap.entrySet()) {
				var boxSum = 0;
				var boxNum = e.getKey();
				var boxNumPower = 1 + boxNum;
				for(int i = 0 ; i< e.getValue().size(); i++) {
					var focalL = e.getValue().get(i).split(" ");
					var v = (i+1) * Integer.parseInt(focalL[1]);
					var boxValue = boxNumPower*v;
					boxSum += boxValue;
				}
				res += boxSum;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		bufferedReader.close();
		System.out.println(res);
	}
}