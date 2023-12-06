import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day5 {
	/*Part 1*/
	public record Triple (long source, long target, long length) {}
	
	List<Triple> seedToSoil = new ArrayList<Triple>();
	List<Triple> soilToFertilizer = new ArrayList<Triple>();
	List<Triple> fertilizerToWater = new ArrayList<Triple>();
	List<Triple> waterToLight = new ArrayList<Triple>();
	List<Triple> lightToTemperature = new ArrayList<Triple>();
	List<Triple> temperatureToHumidity = new ArrayList<Triple>();
	List<Triple> humidityToLocation = new ArrayList<Triple>();
	
	public long calculateMinLocal(String[] seedNumbers) {
		long minLocal = Integer.MAX_VALUE;

		for(var seed : seedNumbers) {
			long s = Long.parseLong(seed.trim());
			System.out.println("seed: " + s);
			
			var soil = s;
			for(var t : seedToSoil) {
				if(t.source <= s && t.source + t.length >= s) {
					soil = t.target + (s - t.source);
				}
			}
			System.out.println("soil: " + soil);
			
			var fert = soil;
			for(var t : soilToFertilizer) {
				if(t.source <= soil && t.source + t.length >= soil) {
					fert = t.target + (soil - t.source);
				}
			}
			System.out.println("fert: " + fert);
			
			var water = fert;
			for(var t : fertilizerToWater) {
				if(t.source <= fert && t.source + t.length >= fert) {
					water = t.target + (fert - t.source);
				}
			}
			System.out.println("water: " + water);
			
			var light = water;
			for(var t : waterToLight) {
				if(t.source <= water && t.source + t.length >= water) {
					light = t.target + (water - t.source);
				}
			}
			System.out.println("light: " + light);

			var temp = light;
			for(var t : lightToTemperature) {
				if(t.source <= light && t.source + t.length >= light) {
					temp = t.target + (light - t.source);
				}
			}
			System.out.println("temp: " + temp);
			
			var humid = temp;
			for(var t : temperatureToHumidity) {
				if(t.source <= temp && t.source + t.length >= temp) {
					humid = t.target + (temp - t.source);
				}
			}
			
			System.out.println("humid: " + humid);

			var local = humid;
			for(var t : humidityToLocation) {
				if(t.source <= humid && t.source + t.length >= humid) {
					local = t.target + (humid - t.source);
				}
			}
			System.out.println("local: " + local);
			
			minLocal = local < minLocal ? local : minLocal;

		}
		return minLocal;
	}
	/*Part 2*/
	public long calculateMinLocalbyPair(String[] seedNumbers) {
		long minLocal = Integer.MAX_VALUE;
		
		for(int i = 0 ; i < seedNumbers.length; i += 2) {
			var seedInit = seedNumbers[i];
			var length = Long.parseLong(seedNumbers[i+1]);
			long se = Long.parseLong(seedInit.trim());
			
			for(long s = se ; s < se + length ; s++) {
				var soil = s;
				for(var t : seedToSoil) {
					if(t.source <= s && t.source + t.length >= s) {
						soil = t.target + (s - t.source);
						break;
					}
				}
			
				var fert = soil;
				for(var t : soilToFertilizer) {
					if(t.source <= soil && t.source + t.length >= soil) {
						fert = t.target + (soil - t.source);
						break;
					}
				}
			
				var water = fert;
				for(var t : fertilizerToWater) {
					if(t.source <= fert && t.source + t.length >= fert) {
						water = t.target + (fert - t.source);
						break;
					}
				}
				
				var light = water;
				for(var t : waterToLight) {
					if(t.source <= water && t.source + t.length >= water) {
						light = t.target + (water - t.source);
						break;
					}
				}

				var temp = light;
				for(var t : lightToTemperature) {
					if(t.source <= light && t.source + t.length >= light) {
						temp = t.target + (light - t.source);
						break;
					}
				}

				var humid = temp;
				for(var t : temperatureToHumidity) {
					if(t.source <= temp && t.source + t.length >= temp) {
						humid = t.target + (temp - t.source);
						break;
					}
				}

				var local = humid;
				for(var t : humidityToLocation) {
					if(t.source <= humid && t.source + t.length >= humid) {
						local = t.target + (humid - t.source);
						break;
					}
				}

				minLocal = local < minLocal ? local : minLocal;
			}
		}
		return minLocal;
	}
	
	public static void main(String[] args) throws IOException {
		Day5 day5 = new Day5();
		FileReader fileReader = new FileReader("input5.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		long res = 0;
		boolean seedToSoil = false;
		boolean soilToFert = false;
		boolean fertToWater = false;
		boolean waterToLight = false;
		boolean lightToTemp = false;
		boolean tempToHumid = false;
		boolean humidToLocal = false;
		
		String[] seedNumbers = null;
		
		try {
			while ((line = bufferedReader.readLine()) != null) {
				if(line.contains("seeds:")){
					seedNumbers = line.replace("seeds: ", "").split(" ");
					continue;
				}
				if(line.contains("seed-to-soil map:")) {
					seedToSoil = true;
					continue;
				}
				if(line.contains("soil-to-fertilizer map:")) {
					seedToSoil = false;
					soilToFert = true;
					continue;
				}
				if(line.contains("fertilizer-to-water map:")) {
					seedToSoil = false;
					soilToFert = false;
					fertToWater = true;
					continue;
				}
				if(line.contains("water-to-light map:")) {
					seedToSoil = false;
					soilToFert = false;
					fertToWater = false;
					waterToLight = true;
					continue;
				}
				if(line.contains("light-to-temperature map:")) {
					seedToSoil = false;
					soilToFert = false;
					fertToWater = false;
					waterToLight = false;
					lightToTemp = true;
					continue;
				}
				if(line.contains("temperature-to-humidity map:")) {
					seedToSoil = false;
					soilToFert = false;
					fertToWater = false;
					waterToLight = false;
					lightToTemp = false;
					tempToHumid = true;
					continue;
				}
				if(line.contains("humidity-to-location map:")) {
					seedToSoil = false;
					soilToFert = false;
					fertToWater = false;
					waterToLight = false;
					lightToTemp = false;
					tempToHumid = false;
					humidToLocal = true;
					continue;
				}

				if(!line.trim().isEmpty()) {
					var l = line.split(" ");
					var target = l[1];
					var targetBeg = Long.parseLong(target);
					var source = l[0];
					var sourceBeg = Long.parseLong(source);
					var length = l[2];
					var len = Long.parseLong(length);
					var t = new Triple(targetBeg, sourceBeg, len);

					if(seedToSoil) {
						day5.seedToSoil.add(t);
					}
					if(soilToFert) {
						day5.soilToFertilizer.add(t);
					}
					if(fertToWater) {
						day5.fertilizerToWater.add(t);
					}
					if(waterToLight) {
						day5.waterToLight.add(t);
					}
					if(lightToTemp) {
						day5.lightToTemperature.add(t);
					}
					if(tempToHumid) {
						day5.temperatureToHumidity.add(t);
					}
					if(humidToLocal) {
						day5.humidityToLocation.add(t);
					}
				}
			}
				
			//res = day5.calculateMinLocal(seedNumbers);
			res = day5.calculateMinLocalbyPair(seedNumbers);
		} catch (IOException e) {
			e.printStackTrace();
		}
        bufferedReader.close();
        System.out.println(res);
	}

}
