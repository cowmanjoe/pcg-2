package com.mygdx.game.input;

import java.util.HashMap;
import java.util.Map;

import com.mygdx.game.input.InputMapping.Range;

public class RangeConverter {
	private Map<Range, Converter> conversionMap; 
	
	public RangeConverter(int numConversions, String[] lines) {
		conversionMap = new HashMap<Range, Converter>(); 
		for (int i = 0; i < numConversions; i++) {
			Range range; 
			Converter converter = new Converter(); 
			
			String[] vals = lines[i].split(" "); 
			
			range = Range.values()[Integer.parseInt(vals[0])];
			converter.minimumInput = Double.parseDouble(vals[1]); 
			converter.maximumInput = Double.parseDouble(vals[2]); 
			converter.minimumOutput = Double.parseDouble(vals[3]); 
			converter.maximumOutput = Double.parseDouble(vals[4]); 
			
			if ((converter.maximumInput < converter.minimumInput || converter.maximumOutput < converter.minimumOutput))
				throw new RuntimeException("Invalid input ranges"); 
			
			conversionMap.put(range, converter); 
		}
	}
	
	public double convert(Range rangeId, double invalue) {
		if (conversionMap.containsKey(rangeId)) {
			return conversionMap.get(rangeId).convert(invalue);
		}
		
		return invalue; 
	}
	
	private class Converter {
		double minimumInput; 
		double maximumInput; 
		
		double minimumOutput; 
		double maximumOutput; 
		
		private double convert(double invalue) {
			double v = invalue; 
			
			if (v < minimumInput) 
				v = minimumInput; 
			else if (v > maximumInput) 
				v = maximumInput; 
			
			double interpolationFactor = (v - minimumInput) / (maximumInput - minimumInput); 
			return ((interpolationFactor * (maximumOutput - minimumOutput)) + minimumOutput);
		}
	}
}