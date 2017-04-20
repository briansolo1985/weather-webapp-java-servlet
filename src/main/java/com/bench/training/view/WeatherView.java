package com.bench.training.view;

import com.bench.training.util.Formatter;
import com.bench.training.util.TemperatureConverter.TemperatureUnit;

public class WeatherView {
	
	public static String makeTemperatureResponseView(String cityName, double temperature, TemperatureUnit unit, boolean defaultCity) {
    	StringBuilder sb = new StringBuilder();
		sb.append(
				defaultCity ? 
				"<div style=\"padding:3px; float:right; border:1px solid black; background:#8FFF6F; \">" : 
				"<div style=\"padding:3px; float:left; border:1px solid black; background:#99C7FF; \">" );
		sb.append(String.format("Actual temperature in %s is %.2f %s", Formatter.capitalize(cityName), temperature, Formatter.capitalize(unit.name())));
		sb.append("</div>");
		
		return sb.toString();
    }
    
	public static String makeErrorView(String message) {
    	StringBuilder sb = new StringBuilder();
		sb.append("<div style=\"padding:3px; float:left; border:1px solid black; background:#FF7373; \">");
		sb.append(message);
		sb.append("</div>");
		
		return sb.toString();
    }

}
