package com.bench.training.exception;

import java.io.IOException;

import com.bench.training.exception.ErrorInfo.ErrorType;
import com.bench.training.exception.ErrorInfo.Severity;

public class ErrorInfoFactory {
	public static final ErrorInfo getIllegalInputParameterErrorInfo(
			String parameterName, Object parameterValue, String errorDescription, String context) {
		
		ErrorInfo info = new ErrorInfo();
		
		info.setErrorId("IllegalInputParameterError");
		info.setContextId(context);
		
		info.setErrorType(ErrorType.CLIENT_ERROR);
		info.setSeverity(Severity.ERROR);
		
		info.setErrorDescription(errorDescription);
		info.setUserErrorDescription("Please provide a valid city name!");
		
		info.setParameter(parameterName, parameterValue);
		
		return info;
	}
	
	public static final ErrorInfo getUrlResourceOperationErrorInfo(
			IOException ioe, String errorDescription, String context) {
		
		ErrorInfo info = new ErrorInfo();
		
		info.setErrorId("UrlResourceOperationError");
		info.setContextId(context);
		
		info.setErrorType(ErrorType.SERVICE_ERROR);
		info.setSeverity(Severity.ERROR);
		
		info.setErrorDescription(errorDescription);
		info.setCause(ioe);
		
		return info;
	}
	
	public static final ErrorInfo getXmlParseErrorInfo(
			Exception e, String errorDescription, String context) {
		
		ErrorInfo info = new ErrorInfo();
		
		info.setErrorId("XmlParseError");
		info.setContextId(context);
		
		info.setErrorType(ErrorType.INTERNAL_ERROR);
		info.setSeverity(Severity.ERROR);
		
		info.setErrorDescription(errorDescription);
		info.setCause(e);
		
		return info;
	}
	
	public static final ErrorInfo getUnsupportedOperationErrorInfo(
			String errorDescription, String context) {
		
		ErrorInfo info = new ErrorInfo();
		
		info.setErrorId("UnsupportedOperationError");
		info.setContextId(context);
		
		info.setErrorType(ErrorType.INTERNAL_ERROR);
		info.setSeverity(Severity.ERROR);
		
		info.setErrorDescription(errorDescription);
		
		return info;
	}

}
