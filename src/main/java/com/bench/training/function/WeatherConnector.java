package com.bench.training.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bench.training.exception.AppException;
import com.bench.training.exception.ErrorInfo.ErrorType;
import com.bench.training.exception.ErrorInfoFactory;

public class WeatherConnector {
	private final int HTTP_OK = 200;
	private final String templateURL = "http://api.openweathermap.org/data/2.5/weather?q=%s&mode=%s";
	private String preparedURL = null;

	public enum ResponseMode {
		XML, JSON;

		public String getUrlName() {
			return this.name().toLowerCase();
		}
	}

	public WeatherConnector(String city, ResponseMode responseMode)
			throws AppException {
		if ((city == null) || ("".equals(city.trim()))) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getIllegalInputParameterErrorInfo(
					"city", city,
					"a valid string must be provided for city name",
					"WeatherConnector/WeatherConnector()"));
			throw ae;
		}
		if ((responseMode == null)) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getIllegalInputParameterErrorInfo(
					"responseMode", responseMode,
					"response mode must be not null",
					"WeatherConnector/WeatherConnector()").setErrorType(
					ErrorType.INTERNAL_ERROR));
			throw ae;
		}
		try {
			preparedURL = String.format(templateURL,
					URLEncoder.encode(city.trim().toLowerCase(), "utf-8"),
					responseMode.getUrlName());
		} catch (UnsupportedEncodingException e) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getIllegalInputParameterErrorInfo(
					"preparedURL", preparedURL, "urlencoding failed",
					"WeatherConnector/WeatherConnector()").setErrorType(
					ErrorType.INTERNAL_ERROR));
			throw ae;
		}
	}

	public String retrieve() throws AppException {
		URL weatherURL = null;
		HttpURLConnection wc = null;
		String inputline = null;
		StringBuilder xmlString = new StringBuilder();
		try {
			weatherURL = new URL(preparedURL);
			wc = (HttpURLConnection) weatherURL.openConnection();
			if (wc.getResponseCode() == HTTP_OK) {
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(wc.getInputStream()))) {
					while ((inputline = br.readLine()) != null) {
						if (!"".equals(inputline)) {
							xmlString.append(inputline);
						}
					}
				}
			} else {
				throw new IOException(
						String.format(
								"url connection error: response code=%d response message=%s",
								wc.getResponseCode(), wc.getResponseMessage()));
			}
		} catch (IOException ioe) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getUrlResourceOperationErrorInfo(ioe,
					"error retrieving weather data",
					"WeatherConnector/retrieve()"));
			throw ae;
		} finally {
			if (wc != null) {
				wc.disconnect();
			}
		}

		// openweather sends error in json although setting was xml response...
		JSONParser parser = new JSONParser();
		try {
			JSONObject jo = (JSONObject) parser.parse(xmlString.toString());
			// if succeeds there is some error
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory
					.getUrlResourceOperationErrorInfo(null,
							"weather service sent back error",
							"WeatherConnector/retrieve()")
					.setErrorType(ErrorType.CLIENT_ERROR)
					.setUserErrorDescription(jo.get("message").toString()));
			throw ae;
		} catch (ParseException e) {
			// probably an xml file
			return xmlString.toString();
		}
	}

}
