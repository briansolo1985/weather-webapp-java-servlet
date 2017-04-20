package com.bench.training.function;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Element;

import com.bench.training.exception.AppException;
import com.bench.training.exception.ErrorInfoFactory;
import com.bench.training.util.SimpleXmlHandler;
import com.bench.training.util.TemperatureConverter;
import com.bench.training.util.TemperatureConverter.TemperatureUnit;

public class WeatherXmlHandler extends SimpleXmlHandler {
	private TemperatureUnit unit = null;

	public WeatherXmlHandler(String xmlString) throws AppException {
		super(xmlString);
		unit = TemperatureUnit.CELSIUS;
	}

	public WeatherXmlHandler(String xmlString, TemperatureUnit unit)
			throws AppException {
		super(xmlString);
		this.unit = unit;
	}

	public void setTemperatureUnit(TemperatureUnit unit) {
		this.unit = unit;
	}

	private String getCurrentCity() throws AppException {
		Element e = getElementByNameAndIndex("city", 0);

		return e.getAttribute("name");
	}

	private double getCurrentTemperature() throws AppException {
		Element e = getElementByNameAndIndex("temperature", 0);

		String tempValue = e.getAttribute("value");
		Number parsedValue = null;
		try {
			parsedValue = NumberFormat.getInstance().parse(tempValue);
		} catch (ParseException pe) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getXmlParseErrorInfo(null,
					"temperature is not valid",
					"WeatherXmlHandler/getCurrentTemperature()").setParameter(
					"tempValue", tempValue));
			throw ae;
		}

		return TemperatureConverter.convert(unit,
				TemperatureConverter.TemperatureUnit.getTemperatureUnit(e
						.getAttribute("unit")), parsedValue.doubleValue());
	}

	private long getLastRetrievedTimeStamp() throws AppException {
		Element e = getElementByNameAndIndex("lastupdate", 0);

		String tempValue = e.getAttribute("value");
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		try {
			long lastUpdateTime = dateformat.parse(tempValue).getTime();
			long timeDifferenceBetweenServers = new Date().getTime()
					- lastUpdateTime;

			return lastUpdateTime + timeDifferenceBetweenServers;
		} catch (ParseException pe) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getXmlParseErrorInfo(null,
					"lastupdate is not valid",
					"WeatherXmlHandler/getLastRetrievedTimeStamp()")
					.setParameter("tempValue", tempValue));
			throw ae;
		}
	}

	public WeatherItem getWeatherData() throws AppException {
		WeatherItem wi = new WeatherItem();

		wi.city = getCurrentCity();
		wi.temperature = getCurrentTemperature();
		wi.unit = unit;
		wi.lastUpdated = getLastRetrievedTimeStamp();

		return wi;
	}
}
