package com.bench.training.function;

import com.bench.training.util.TemperatureConverter.TemperatureUnit;

public class WeatherItem {
	public String			city;
	public double			temperature;
	public TemperatureUnit	unit;
	public long				lastUpdated;
	
	@Override
	public String toString() {
		return String.format("city=%s temperature=%f unit=%s lastupdated=%d", city, temperature, unit, lastUpdated);
	}
}
