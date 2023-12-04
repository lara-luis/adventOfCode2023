import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
	/*Part 1*/
	public int getCardPoints(String card) {
		String[] numbers = card.split(" | ");
		var numbersList = Arrays.asList(numbers); 
		int splitIndex = numbersList.indexOf("|");
		List<String> winningNumbers = numbersList.subList(0, splitIndex);
		List<String> myNumbers = numbersList.subList(splitIndex+1,numbersList.size());
		int points = 0;
		
		for(var n : winningNumbers) {
			if(!n.isEmpty()) {
				if(myNumbers.contains(n)) {
					if(points == 0) {
						points += 1;	
					} else {
						points = points*2;	
					}
				}
			}
		}
		
		return points;
		
	}

	/*Part 2*/
	Map<Integer, Integer> cardsCount = new HashMap<Integer, Integer>();
	public void calculateCardPoints(int cardNum, String card) {
		String[] numbers = card.split(" | ");
		var numbersList = Arrays.asList(numbers); 
		int splitIndex = numbersList.indexOf("|");
		List<String> winningNumbers = numbersList.subList(0, splitIndex);
		List<String> myNumbers = numbersList.subList(splitIndex+1,numbersList.size());
		int points = 0;
		
		for(var n : winningNumbers) {
			if(!n.isEmpty()) {
				if(myNumbers.contains(n)) {
					points += 1;
				}
			}
		}
		
		var currCardCount = 1;
		if(cardsCount.containsKey(cardNum)){
			currCardCount = cardsCount.get(cardNum)+1;
			cardsCount.put(cardNum, currCardCount);
		} else {
			cardsCount.put(cardNum, 1);
		}
		
		for(int i = cardNum+1; i <= cardNum + points; i++) {
			if(cardsCount.containsKey(i)) {
				var cardCount = cardsCount.get(i);
				cardsCount.put(i, cardCount + currCardCount);
			} else {
				cardsCount.put(i, currCardCount);
			}
		}
	}
	
	public int countFinalSum() {
		int res = 0;
		for(var k : cardsCount.entrySet()) {
			res += k.getValue();
		}
		return res;
	}
	
	public static void main(String[] args) throws IOException {
		Day4 day4 = new Day4();
		FileReader fileReader = new FileReader("input4.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		int res = 0;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				String[] card = line.split(":");
				///*part1*/res += day4.getCardPoints(card[1].trim());
				day4.calculateCardPoints(Integer.parseInt(card[0].replace("Card ","").trim()),card[1].trim());
			}
			res = day4.countFinalSum();
		} catch (IOException e) {
			e.printStackTrace();
		}
        bufferedReader.close();
        System.out.println(res);
	}

}
