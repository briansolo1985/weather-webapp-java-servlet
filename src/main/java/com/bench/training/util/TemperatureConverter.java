package com.bench.training.util;

import java.util.HashMap;
import java.util.Map;

import com.bench.training.exception.AppException;
import com.bench.training.exception.ErrorInfo.ErrorType;
import com.bench.training.exception.ErrorInfoFactory;

public class TemperatureConverter {
	public enum TemperatureUnit {
		CELSIUS, KELVIN, FAHRENHEIT;
		
		private static final Map<String, TemperatureUnit> unitNameMap;
		
		static {
			unitNameMap = new HashMap<>();
			unitNameMap.put("celsius", CELSIUS);
			unitNameMap.put("fahrenheit", FAHRENHEIT);
			unitNameMap.put("kelvin", KELVIN);
		}
		
		public static TemperatureUnit getTemperatureUnit(String unit) throws AppException {
			if( (unit == null) ) {
				AppException ae = new AppException();
				ae.addInfo(
						ErrorInfoFactory.getIllegalInputParameterErrorInfo(
								"unit", unit, "unit parameter must be not null",
								"TemperatureConverter/TemperatureUnit/getTemperatureUnit()").setErrorType(ErrorType.INTERNAL_ERROR) );
				throw ae;
			}
			unit = unit.trim().toLowerCase();
			if( !unitNameMap.containsKey(unit) ) {
				AppException ae = new AppException();
				ae.addInfo(
						ErrorInfoFactory.getIllegalInputParameterErrorInfo(
								"unit", unit, "invalid unit name",
								"TemperatureConverter/TemperatureUnit/getTemperatureUnit()").setErrorType(ErrorType.INTERNAL_ERROR) );
				throw ae;
			}
			
			return unitNameMap.get(unit);
		}
	}
	
	public static double convert(TemperatureUnit source, TemperatureUnit target, double value) throws AppException {
		double calculatedValue = 0.0f;
		if( (source == null) ) {
			AppException ae = new AppException();
			ae.addInfo(
					ErrorInfoFactory.getIllegalInputParameterErrorInfo(
							"source", source, "source parameter must be not null",
							"TemperatureConverter/convert()").setErrorType(ErrorType.INTERNAL_ERROR) );
			throw ae;
		}
		if( (target == null) ) {
			AppException ae = new AppException();
			ae.addInfo(
					ErrorInfoFactory.getIllegalInputParameterErrorInfo(
							"target", target, "target parameter  must be not null",
							"TemperatureConverter/convert()").setErrorType(ErrorType.INTERNAL_ERROR) );
			throw ae;
		}
		
		if( source == target ) {
			calculatedValue = value;
		} else {
			if( source == TemperatureUnit.KELVIN ) {
				if( target == TemperatureUnit.CELSIUS ) {
					calculatedValue = value + 273.15;
				} else if( target == TemperatureUnit.FAHRENHEIT ) {
					calculatedValue = (value + 273.15 - 32) * 5 / 9;
				} else {
					AppException ae = new AppException();
					ae.addInfo(
							ErrorInfoFactory.getUnsupportedOperationErrorInfo(
									"invalid target temperature unit", "TemperatureConverter/convert()").setParameter("target", target));
					throw ae;
				}
			} else if( source == TemperatureUnit.CELSIUS ) {
				if( target == TemperatureUnit.KELVIN ) {
					calculatedValue = value - 273.15;
				} else if( target == TemperatureUnit.FAHRENHEIT ) {
					calculatedValue = (value - 32) * 5 / 9;
				} else {
					AppException ae = new AppException();
					ae.addInfo(
							ErrorInfoFactory.getUnsupportedOperationErrorInfo(
									"invalid target temperature unit", "TemperatureConverter/convert()").setParameter("target", target));
					throw ae;
				}
			} else if( source == TemperatureUnit.FAHRENHEIT ) {
				if( target == TemperatureUnit.CELSIUS ) {
					calculatedValue = (value * 9 / 5) + 32; 
				} else if( target == TemperatureUnit.KELVIN ) {
					calculatedValue = (value * 9 / 5) + 32 - 273.15;
				} else {
					AppException ae = new AppException();
					ae.addInfo(
							ErrorInfoFactory.getUnsupportedOperationErrorInfo(
									"invalid target temperature unit", "TemperatureConverter/convert()").setParameter("target", target));
					throw ae;
				}
			} else {
				AppException ae = new AppException();
				ae.addInfo(
						ErrorInfoFactory.getUnsupportedOperationErrorInfo(
								"invalid source temperature unit", "TemperatureConverter/convert()").setParameter("source", source));
				throw ae;
			}
		}
		
		return calculatedValue;
	}
}
