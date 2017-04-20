package com.bench.training.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bench.training.exception.AppException;
import com.bench.training.function.WeatherConnector;
import com.bench.training.function.WeatherItem;
import com.bench.training.function.WeatherXmlHandler;
import com.bench.training.view.WeatherView;

public class DefaultCityFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		final String 			city	= "New York";
		
		try {
			arg2.doFilter(arg0, arg1);
		} finally {
			HttpServletRequest	request		= (HttpServletRequest)	arg0;
			HttpServletResponse response	= (HttpServletResponse) arg1;
			response.setContentType("text/html");
			try {
				logger.debug("default city=[{}]", city);
				
				WeatherConnector wc = new WeatherConnector(city, WeatherConnector.ResponseMode.XML);
				String weatherXmlString = wc.retrieve();
				
				WeatherXmlHandler	wxh = new WeatherXmlHandler(weatherXmlString);
				WeatherItem			wi	= wxh.getWeatherData();
				logger.debug("default weather: {}", wi);
				request.getSession().setAttribute("defaultWeather", wi);
				
				response.getWriter().println(WeatherView.makeTemperatureResponseView(wi.city, wi.temperature, wi.unit, true));
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
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
