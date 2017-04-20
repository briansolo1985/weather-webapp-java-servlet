package com.bench.training.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ErrorInfo {
	public enum ErrorType {
		CLIENT_ERROR, SERVICE_ERROR, INTERNAL_ERROR
	}

	public enum Severity {
		FATAL, ERROR, WARNING
	}

	protected Throwable cause = null;
	protected String errorId = null;
	protected String contextId = null;

	protected ErrorType errorType = null;
	protected Severity severity = null;

	protected String userErrorDescription = null;
	protected String errorDescription = null;
	protected String errorCorrection = null;

	protected Map<String, Object> parameters = new HashMap<>();

	public String getDevLogString() {
		StringBuilder sb = new StringBuilder();

		if (cause != null) {
			sb.append(String.format("1. Cause:    %n"));
			sb.append(String.format("	class:    %s%n", cause.getClass()
					.getName()));
			sb.append(String.format("	message:  %s%n", cause.getMessage()));
		}
		if (errorId != null) {
			sb.append(String.format("2. ErrorId:   %s%n", errorId));
		}
		if (contextId != null) {
			sb.append(String.format("3. ContextId: %s%n", contextId));
		}
		if (errorType != null) {
			sb.append(String.format("4. ErrorType: %s%n", errorType.name()));
		}
		if (severity != null) {
			sb.append(String.format("5. Severity:  %s%n", severity.name()));
		}

		if (userErrorDescription != null) {
			sb.append(String.format("6. UserErrorDescription:  %s%n",
					userErrorDescription));
		}
		if (errorDescription != null) {
			sb.append(String.format("7. ErrorDescription:      %s%n",
					errorDescription));
		}
		if (errorCorrection != null) {
			sb.append(String.format("8. ErrorCorrection:       %s%n",
					errorCorrection));
		}
		if (!parameters.isEmpty()) {
			sb.append(String.format("9. Parameters:%n"));
			for (String s : parameters.keySet()) {
				String v = parameters.get(s).toString();
				sb.append(String.format("    name=%s value=%s%n", s, v));
			}
		}

		return sb.toString();
	}

	public String getUserLogString() {
		return (errorType == ErrorType.CLIENT_ERROR) ? userErrorDescription
				: "";
	}

	public Throwable getCause() {
		return cause;
	}

	public ErrorInfo setCause(Throwable cause) {
		this.cause = cause;
		return this;
	}

	public String getErrorId() {
		return errorId;
	}

	public ErrorInfo setErrorId(String errorId) {
		this.errorId = errorId;
		return this;
	}

	public String getContextId() {
		return contextId;
	}

	public ErrorInfo setContextId(String contextId) {
		this.contextId = contextId;
		return this;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public ErrorInfo setErrorType(ErrorType errorType) {
		this.errorType = errorType;
		return this;
	}

	public Severity getSeverity() {
		return severity;
	}

	public ErrorInfo setSeverity(Severity severity) {
		this.severity = severity;
		return this;
	}

	public String getUserErrorDescription() {
		return userErrorDescription;
	}

	public ErrorInfo setUserErrorDescription(String userErrorDescription) {
		this.userErrorDescription = userErrorDescription;
		return this;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public ErrorInfo setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
		return this;
	}

	public String getErrorCorrection() {
		return errorCorrection;
	}

	public ErrorInfo setErrorCorrection(String errorCorrection) {
		this.errorCorrection = errorCorrection;
		return this;
	}

	public Map<String, Object> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}

	public ErrorInfo setParameter(String key, Object parameter) {
		this.parameters.put(key, parameter);
		return this;
	}
}
