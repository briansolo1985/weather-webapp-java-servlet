package com.bench.training.filter;

import java.io.IOException;
import java.util.Date;

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

import com.bench.training.function.WeatherItem;
import com.bench.training.view.WeatherView;

public class UpdateCacheFilter implements Filter {
	private final static long UPDATE_PROHIBIT_INTERVAL_MS = 3_600_000;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		try {
			HttpServletRequest  request  = (HttpServletRequest)  arg0;
			HttpServletResponse response = (HttpServletResponse) arg1;
			
			Object ocw = request.getSession().getAttribute("currentWeather");
			Object odw = request.getSession().getAttribute("defaultWeather");
			
			String city =  request.getParameter("city");
			logger.debug("current city=[{}]", city.trim().toLowerCase());
			
			if( (ocw == null) || (odw == null) ) {
				logger.debug("CANNOT CHECK: some weather item is missing, passing to servlet to calculate" );
			} else if( !(ocw instanceof WeatherItem) || !(odw instanceof WeatherItem) ) {
				logger.debug("CANNOT CHECK: incompatible data ocw={} odw={}", ocw.getClass().getName(), odw.getClass().getName() );
			} else {
				WeatherItem cw = (WeatherItem) request.getSession().getAttribute("currentWeather");
				WeatherItem dw = (WeatherItem) request.getSession().getAttribute("defaultWeather");
				
				logger.debug("previous city=[{}]", cw.city.trim().toLowerCase());
				if( city.trim().toLowerCase().equals(cw.city.trim().toLowerCase()) ) {
					long difference = new Date().getTime() - dw.lastUpdated;
					if( difference > UPDATE_PROHIBIT_INTERVAL_MS ) {
						logger.debug("CHECKED: update can go, interval=" + difference );
					} else {
						logger.debug("CHECKED: update is prohibited working from cache, interval=" + difference );
						response.getWriter().println(WeatherView.makeTemperatureResponseView(cw.city, cw.temperature, cw.unit, false));
						response.getWriter().println(WeatherView.makeTemperatureResponseView(dw.city, dw.temperature, dw.unit, true));
						response.flushBuffer();
						return;
					}
				} else {
					logger.debug("CHECKED: city changed, update can go" );
				}
			}
			arg2.doFilter(arg0, arg1);
		} finally {			
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
