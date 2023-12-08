import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day7 {
	/*Part 1*/
	List<String> cardsStrengthOrder = Arrays.asList(
			"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A");
	
	Map<Integer, List<String>> ranks = new HashMap<Integer,List<String>>();
	public record Pair (List<String> v1, List<String> v2) {}
	
	
	
	public void processHandType(String hand) {
		var handArr = hand.split("");
		List asList = Arrays.asList(handArr);
		Set<String> mySet = new HashSet<String>(asList);
		List<Integer> frequencies = new ArrayList<Integer>();
		for(String s: mySet){
			 var freq = Collections.frequency(asList,s);
			 frequencies.add(freq);
		} 
		
		var typeIndex = 0;
		if(frequencies.contains(5)) {
			typeIndex = 6;
		}
		else if(frequencies.contains(4)) {
			typeIndex = 5;
		}
		else if(frequencies.contains(3) && frequencies.contains(2)) {
			typeIndex = 4;
		}
		else if(frequencies.contains(3) && frequencies.contains(1)) {
			typeIndex = 3;
		}
		else if(frequencies.indexOf(2) != frequencies.lastIndexOf(2) && frequencies.contains(1)) {
			typeIndex = 2;
		}
		else if(frequencies.contains(2)) {
			typeIndex = 1;
		} 
		else {
			typeIndex = 0;
		}
		
		if(ranks.containsKey(typeIndex)) {
			var t = ranks.get(typeIndex);
			t.add(hand);
			ranks.put(typeIndex, t);
		}else {
			var l = new ArrayList<String>();
			l.add(hand);
			ranks.put(typeIndex, l);
		}
	}	
	
	public class ObjectComparator implements Comparator{

		public int compare(Object o1, Object o2) {
			String myObj1 = (String)o1;
			String myObj2 = (String)o2;
			
			for(int i = 0 ; i < 5 ; i++) {
				var idx1 = cardsStrengthOrder.indexOf(myObj1.charAt(i)+"");
				var idx2 = cardsStrengthOrder.indexOf(myObj2.charAt(i)+"");
				
				if(idx1 > idx2) {
					return 1;
				}
				if(idx1 < idx2) {
					return -1;
				}
			}
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getSortedCards(List<String> cards) {
		cards.sort(new ObjectComparator());
		return cards;
	}
	
	public ArrayList<String> getOrderedList() {
		var list = new ArrayList<String>();
		for(var p : ranks.entrySet()) {
			var t = p.getValue();
			list.addAll(getSortedCards(t));
		}
		return list;
	}

	/*Part 2*/
	List<String> cardsStrengthOrder_2 = Arrays.asList(
			"J", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A");
	
	public void processHandType_2(String hand) {
		var handArr = hand.split("");
		List asList = Arrays.asList(handArr);
		Set<String> mySet = new HashSet<String>(asList);
		List<Integer> frequencies = new ArrayList<Integer>();

		var typeIndex = 0;
		var maxFreq = -1;
		var maxFreqChar = "";
		for(String s: mySet){
			var freq = Collections.frequency(asList,s);
			frequencies.add(freq);
			
			if(freq == 5 && s.equals("J")) {
				maxFreqChar = "J";
				maxFreq = 5;
			}
			else if(freq > maxFreq && !s.equals("J")) {
				maxFreq = freq;
				maxFreqChar = s;
			} else if (freq == maxFreq) {
				if(cardsStrengthOrder_2.indexOf(s) > cardsStrengthOrder_2.indexOf(maxFreqChar)
						&& !s.equals("J")) {
					maxFreq = freq;
					maxFreqChar = s;
				}
			}
		}
		
		var newHand = hand;
		if(hand.contains("J")) {
			newHand = hand.replace("J", maxFreqChar);			
			handArr = newHand.split("");
			asList = Arrays.asList(handArr);
			mySet = new HashSet<String>(asList);
			frequencies = new ArrayList<Integer>();

			for(String s: mySet){
				var freq = Collections.frequency(asList,s);
				frequencies.add(freq);
			}
		}

		if(frequencies.contains(5)) {
			typeIndex = 6;
		}
		else if(frequencies.contains(4)) {
			typeIndex = 5;
		}
		else if(frequencies.contains(3) && frequencies.contains(2)) {
			typeIndex = 4;
		}
		else if(frequencies.contains(3) && frequencies.contains(1)) {
			typeIndex = 3;
		}
		else if(frequencies.indexOf(2) != frequencies.lastIndexOf(2) && frequencies.contains(1)) {
			typeIndex = 2;
		}
		else if(frequencies.contains(2)) {
			typeIndex = 1;
		} 
		else {
			typeIndex = 0;
		}

		if(ranks.containsKey(typeIndex)) {
			var t = ranks.get(typeIndex);
			t.add(hand);
			ranks.put(typeIndex, t);
		}else {
			var l = new ArrayList<String>();
			l.add(hand);
			ranks.put(typeIndex, l);
		}
	}	
	
	public class ObjectComparator_2 implements Comparator{

		public int compare(Object o1, Object o2) {
			String myObj1 = (String)o1;
			String myObj2 = (String)o2;
			
			for(int i = 0 ; i < 5 ; i++) {
				var idx1 = cardsStrengthOrder_2.indexOf(myObj1.charAt(i)+"");
				var idx2 = cardsStrengthOrder_2.indexOf(myObj2.charAt(i)+"");
				
				if(idx1 > idx2) {
					return 1;
				}
				if(idx1 < idx2) {
					return -1;
				}
			}
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getSortedCards_2(List<String> cards) {
		cards.sort(new ObjectComparator_2());
		return cards;
	}
	
	public ArrayList<String> getOrderedList_2() {
		var list = new ArrayList<String>();
		for(var p : ranks.entrySet()) {
			var t = p.getValue();
			list.addAll(getSortedCards_2(t));
		}
		return list;
	}
	
	public static void main(String[] args) throws IOException {
		Day7 day7 = new Day7();
		FileReader fileReader = new FileReader("input7.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		Map<String, Integer> ranks = new HashMap<String, Integer>();
		int res = 0;
		var orderedList = new ArrayList<String>();
		try {
			while ((line = bufferedReader.readLine()) != null) {
				var lineSplit = line.split(" ");
				var hand = lineSplit[0];
				var bid = Integer.parseInt(lineSplit[1]);
				ranks.put(hand, bid);
				///*Part 1*/ day7.processHandType(hand);
				day7.processHandType_2(hand);
			}
			///*Part1*/orderedList = day7.getOrderedList();
			orderedList = day7.getOrderedList_2();
			
			for(var j = 1; j <= orderedList.size() ; j++) {
				var currRes = j * ranks.get(orderedList.get(j-1));
				res += currRes;
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        bufferedReader.close();
        System.out.println(res);
	}

}
