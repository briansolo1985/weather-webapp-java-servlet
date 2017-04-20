package com.bench.training.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bench.training.exception.AppException;
import com.bench.training.function.WeatherConnector;
import com.bench.training.function.WeatherItem;
import com.bench.training.function.WeatherXmlHandler;
import com.bench.training.view.WeatherView;

public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(WeatherServlet.class);
       
    public WeatherServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		try {
			String city =  request.getParameter("city");
			logger.debug("current city=[{}]",city);
			
			WeatherConnector wc = new WeatherConnector(city, WeatherConnector.ResponseMode.XML);
			String weatherXmlString = wc.retrieve();
			
			WeatherXmlHandler	wxh = new WeatherXmlHandler(weatherXmlString);
			WeatherItem			wi	= wxh.getWeatherData();
			logger.debug("current weather: {}", wi);
			request.getSession().setAttribute("currentWeather", wi);
			
			response.getWriter().println(WeatherView.makeTemperatureResponseView(wi.city, wi.temperature, wi.unit, false));
		} catch (AppException ae) {
			logger.error("{}", ae);
			response.getWriter().println(WeatherView.makeErrorView(
					("".equals(ae.getUserLog())) ? "Ooops... Something went wrong, please check error log!" : ae.getUserLog() ));
		} catch (Throwable t) {
			logger.error("{}", t);
			response.getWriter().println(WeatherView.makeErrorView("Ooops... Something went wrong, please check error log!"));
		} finally {
			response.flushBuffer();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
